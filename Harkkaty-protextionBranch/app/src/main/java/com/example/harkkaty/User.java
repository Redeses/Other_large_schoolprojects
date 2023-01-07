package com.example.harkkaty;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

//is used to hold the current user of the app and his info aswell as methods that allow updating the infromation and setting it
public class User {
    private String name;
    private String email;
    private String userID;
    private String phoneNumberM;
    private String address;
    private int age;
    private String birthDate;
    private String userName;
    private ArrayList<Account> accounts;
    //info list that keeps users info in a list that can be used in other classes
    private ArrayList<String> infoList;
    private StringUtility  stringu;
    private SQLUtility sql;

    private Account acc;
    private static User user = new User();

    private User(){
        name="";
        email = "";
        userID = "";
        phoneNumberM = "";
        address = "";
        birthDate = "";
        accounts = null;
        userName="";
        stringu = StringUtility.getInstance();
        sql = SQLUtility.getSQLUtil(null);
    }

    //used when logging out
    public void resetUser(){
        name="";
        email = "";
        userID = "";
        phoneNumberM = "";
        address = "";
        birthDate = "";
        accounts = null;
        userName="";
        stringu = StringUtility.getInstance();
        sql = SQLUtility.getSQLUtil(null);
    }

    public static User getCurrentUser(){
        return user;
    }


    public ArrayList<String> getAllInfo(){
        return infoList;
    }

    public void updateUserInfo(ArrayList<String> newUserInfo, String id){
        infoList = newUserInfo;
        setCurrentUser();
        sql.updateUserInfo(infoList.get(2),infoList.get(3),infoList.get(4),infoList.get(5), id);

    }

    //the order the info lists info is name(full), birthdate, country, address, email, phonenumber, username
    public void setCurrentUser(){
        name =infoList.get(0);
        birthDate = infoList.get(1);
        address = infoList.get(3)+ " "+ infoList.get(2);
        email = infoList.get(4);
        phoneNumberM = infoList.get(5);
        userName=infoList.get(6);



    }

    public void addCurrentUser(String id){
        infoList=sql.getProfileInfo(id);
        setCurrentUser();
        setAccounts();
    }

    //method to set userID
    public void setUserID(String ID){
        userID= ID;
    }

    //method which gets all users account data from sqlFile and adds it to here, is run when new user is made
    public void setAccounts(){
        accounts = sql.getAccounts(userID);
    }

    //used for making String[] array size
    public int getAccountAmount(){
        if (accounts ==null){
            return 0;
        }
        return accounts.size();
    }

    public ArrayList<Account> getAccounts(){
        return accounts;
    }

    public void addAccount(boolean savings, String balanceString){
        String accountID =stringu.makeAccountID(userID, savings);
        int proxy;
        sql.addAccount(accountID, userID, savings, balanceString);
        Account acc = new Account();
        if(savings){
            proxy = 1;
        }else{
            proxy = 0;
        }
        acc.setAccount(accountID, proxy, balanceString);
        System.out.println(acc==null);
        if (accounts==null){
            accounts= new ArrayList<Account>();
        }
        accounts.add(acc);
    }

    //this methdo gets account based on teh spinner string sent here
    public Account getSelectedAccount(String spinneString){
        Account proxyAccount;
        String id;
        String[] str = new String[2];
        str = spinneString.split(":");
        id = str[0];
        for (int i = 0; i<accounts.size() ; i++){
            proxyAccount=accounts.get(i);
            if (proxyAccount.getAccountNumber().equals(id)){
                return proxyAccount;
            }
        }
        return null;
    }

    //returns accounts position in teh spinner base on a string given
    public int getAccountNumberInSpinner(String number){
        int i=0;
        for (Account acc: accounts){
            if (acc.getAccountNumber().equals(number)){
                return i;
            }
            i++;
        }
        return 0;
    }

    public Account getAccountByNumber(String accountNumber){
        for (Account acc: accounts){
            if(accountNumber.equals(acc.getAccountNumber())){
                return acc;
            }
        }
        return null;
    }
}
