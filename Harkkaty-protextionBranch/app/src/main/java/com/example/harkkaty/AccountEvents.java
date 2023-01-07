package com.example.harkkaty;

import java.util.Date;


//class which holds event information
//if the current account is receiving then receiving is you and message is type is incoming;
public class AccountEvents {
    private Date eventDate;
    private String receivingAccount;
    private double amount;
    private String accountID, message, Entity, eventID;

    private StringUtility stringU;
    private DateC datec;

    public AccountEvents(){
        eventDate=null;
        receivingAccount="";
        amount = 0.0;
        message="";
        Entity="";
        eventID="";
        accountID="";
        datec = DateC.getDatec();
        stringU = StringUtility.getInstance();
    }

    //set accont event
    public void AccountEvents(Date date, String Raccount, double Amount, String message, String entity, String id){
        eventDate= date;
        receivingAccount=Raccount;
        amount=Amount;
        this.message = message;
        Entity=entity;
        eventID=stringU.makeACardNumber(Raccount);
        accountID =id;

    }

    public Date getEventDate(){return eventDate;}
    public String getDateString(){return datec.getSimpleDate(eventDate);}

    public String getReceivingAccount(){
        return receivingAccount;
    }
    public double getAmount(){
        return amount;
    }
    public String getmessage(){
        return message;
    }
    public String getEntity(){return Entity;}
    public String getID(){return eventID;}
    public String getAccountID(){return accountID;}
}
