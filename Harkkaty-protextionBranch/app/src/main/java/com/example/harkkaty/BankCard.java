package com.example.harkkaty;

import java.io.Serializable;

//class which has bankCards non important info on it
public class BankCard implements Serializable {

    private String cardNumber;
    //type is either credit, debit or credit&debit
    private String type;
    //in limits if the limit is a -1 then there is no limit
    private int onlineLimit;
    private int cashLimit;
    private int checkingLimit;
    private int credit;
    private String account;

    private SQLUtility sql;
    private StringUtility stringU;

    public BankCard(){
        cardNumber="default";
        type="";
        onlineLimit=-1;
        cashLimit=-1;
        checkingLimit=-1;
        credit =0;//no credit
        account = "";
        sql = SQLUtility.getSQLUtil(null);
        stringU = StringUtility.getInstance();
    }


    //sets the current instance of the bankcard
    public void setBankCard(String cardN, String type, int onlineLimit, int cashLimit, int checkingLimit, int credit, String accountID){
        cardNumber=cardN;
        this.type=type;
        this.onlineLimit=onlineLimit;
        this.cashLimit = cashLimit;
        this.checkingLimit = checkingLimit;
        this.credit = credit;
        this.account = accountID;
    }

    public String getNumber(){
        return cardNumber;
    }

    public String getType(){
        return type;
    }

    //get methods for limits
    public int getOnlineLimit (){return onlineLimit;}
    public int getCashLimit (){return cashLimit;}
    public int getCheckingLimit (){return checkingLimit;}
    public int getCredit (){return credit;}

    //set methods for limits
    public void setOnlineLimit(int i){onlineLimit =i;}
    public void setCashLimit(int i){cashLimit = i;}
    public  void setCheckingLimit(int i){checkingLimit =i;}
    public void setCredit(int i){credit = i;}


    //next method is used to update sql database with the limits
    public void updateSQL(){
        sql.updateLimits(this);
    }

    //method for setting getting account
    public String getAccount(){return account;}
    public void setAccount(String accountID){account=accountID;}

    public String getCardType(){
        return type;
    }
    public void setCardType(String newType){
        type=newType;
    }

}
