package com.example.harkkaty;
//make chekc email funktion, check address, check birthdate and save items to somewhere else

import java.util.ArrayList;

// is a utilisty class with methods to help loo if personal info is good or correctly formatted
public class personalInfoUtility {
    String proxy;

    //the following keeps tract of a list that chekcs whether all personal info thing have been met
    //its compromised of boolean values where [0] value is first name,[1] is last name
    //[2] date of birth, [3] country, [4] address, [5] email and [6] is phoneNumber
    ArrayList<Boolean> readyList = new ArrayList<>();

    //Arraylist where personal info is held until the whole account has been made at which point it will be save to sql
    ArrayList<String> allInfoList = new ArrayList<String>();


    private static personalInfoUtility person = new personalInfoUtility();

    //bringing stringutility class usable here
    private StringUtility SUtil = StringUtility.getInstance();
    private SQLUtility sql;

    private personalInfoUtility(){
        setLists();

    }

    // method which resets the lists also use during construction
    private void setLists(){
        for (int i=0; i<7; i++){
            allInfoList.add("");
            readyList.add(false);

        }
    }

    public static personalInfoUtility getInstance(){
        return person;
    }

    //checks emaiil for problems
    public boolean checkEmail(String email){
        boolean check;
        check = SUtil.checkforAtSign(email);
        if(check==false){
            return false;
        }

        return true;
    }

    //check if all the boxes have been filled AKA if readylist is all trues
    public boolean checkInfo(){
        for (int i=0; i<7; i++){

            if(readyList.get(i)==false){

                return false;
            }
        }
        return true;
    }

    // adds to boolean list and infolist the given position info
    public void addToLists(String info, int position){
        readyList.set(position, true);
        allInfoList.set(position, info);
    }

    //removes certain position info from lists
    public void removeFromLists(int position){
        readyList.set(position, false);
        allInfoList.set(position, "");
    }

    //sends allinfoList to be made into a user class/xml-file  and also the username and password are passed to database
    //is called from class TunnusLuonti and the method is forward clicklistener


    //empties both lists for next use
    public void emptylists(){
        for (int i=0; i<7; i++){
            allInfoList.set(i,"");
            readyList.set(i, false);
        }
        setLists();
    }

    //method which returns booleanLists vlue for the spot, it's used for telling which parts of the thing are not filled in
    public boolean returnBool(int x){
        return readyList.get(x);
    }

    //method that is used for finding if names, phone and address fields are filled and putting them into
    // the lists
    public void AddAllToList(String fName, String lName, String phone, String address){
        if (fName.equals("")){

        }else {
            addToLists(fName, 0);
        }
        if (lName.equals("")){

        }else{
            addToLists(lName, 1);
        }
        if (phone.equals("")){

        }else{
            addToLists(phone, 6);
        }
        if (address.equals("")){

        }else{
            addToLists(address, 4);
        }

    }

    public ArrayList<String> getAllInfoList(){
        return allInfoList;
    }


}
