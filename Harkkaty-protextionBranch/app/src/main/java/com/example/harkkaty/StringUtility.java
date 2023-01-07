package com.example.harkkaty;

import java.util.ArrayList;

//a class for string manipulation
public class StringUtility {

    private String letters;

    private DateC dateU= DateC.getDatec();

    private static StringUtility SUtil = new StringUtility();
    private StringUtility(){

        dateU = dateU.getDatec();
        letters = "ABCDEFGHIJKLMNOPQRSTYVWXYZ";
    }

    public static StringUtility getInstance(){
        return SUtil;
    }


    private char randomLetter(){
        char letter;
        int proxy;
        proxy = (int) (Math.random()*((25-0)+1));
        letter = (char) letters.charAt(proxy);
        return letter;
    }

    //makes a user id
    public String makeID(String user){
        String ID="";
        char proxyChar;
        int proxyInt, lenghtUser;
        lenghtUser = user.length();
        ID= ID + randomLetter();
        proxyChar = user.charAt(lenghtUser-1);
        if (Character.isDigit(proxyChar)==true){
            proxyInt = (int) (Math.random()*(10));
            ID=ID + new Integer(proxyInt).toString();
        }else{
            ID = ID +randomLetter();
        }
        ID = ID + new Integer((int) (Math.random()*(99))).toString();
        ID = ID+ randomLetter();
        return ID;
    }

    // checks if email has one and only one of @ signs
    public boolean checkforAtSign(String email){
        char sign;
        int x=0;
        for (int i=0; i<email.length(); i++){
            sign=email.charAt(i);
            if ((sign=='@')==true){
                x++;
            }

        }
        if(x==1){
            return true;
        }
        else {
            return false;
        }
    }

    /*method that allows a Account object to be made into a form of
    = AccountNumber
    €          accountBalance
    */
    public String makeAccountToString(Account acc){
        String proxy="";
        proxy= acc.getAccountNumber()+":   "+ acc.getBalance()+ "€";
        return proxy;
    }


    //helper method for making the accountID
    private String makeFourDigitNumber(){
        int random;
        String proxy="";
        while (proxy.length()<4){
            random = (int) (Math.random()*(10));
            proxy=proxy+ Integer.toString(random);
        }
        return proxy;
    }

    //next if method which makes a id for account AKA a bankNumber so only digits are allowed
    public String makeAccountID(String userName, boolean type){
        String proxy = "1267";
        if(type){
            proxy= proxy+" 1550";
        }else{
            proxy=proxy+" 4787";
        }
        int size = userName.length();
        proxy =proxy+ " "+ makeFourDigitNumber()+ " "+ makeFourDigitNumber();
        return proxy;
    }

    //method used in accountActivity activity where the string this returns is used in recycleView
    public String[] getFourEvents(ArrayList<AccountEvents> arrayAE){
        String[] str4= new String[4];
        String proxy;
        int uplimit, lowerlimit, proxyInt;
        AccountEvents proxyEvent;
        uplimit=arrayAE.size();
        if(arrayAE.size()<4){
            lowerlimit=0;
        }else{
            lowerlimit=uplimit-4;
        }
        proxyInt=0;
        for(int i= lowerlimit; i<uplimit; i++){
            proxyEvent = arrayAE.get(i);
            proxy= proxyEvent.getReceivingAccount()+"   " + dateU.getSimpleDate(proxyEvent.getEventDate()) + "  "+ proxyEvent.getAmount();
            str4[proxyInt] = proxy;
            proxyInt++;
        }
        return str4;
    }


    //method used for getting cards from account into a string list
    public String[] getCards(ArrayList<BankCard> arrayCards){
        if (arrayCards==null){
            String[] str = new String[0];
            return str;
        }
        String[] str = new String[arrayCards.size()];
        String proxy;
        BankCard proxyCard;
        for(int i= 0; i<arrayCards.size(); i++){
            proxyCard = arrayCards.get(i);
            proxy = proxyCard.getNumber() +"    "+ proxyCard.getType();
            str[i] = proxy;
        }
        return str;
    }


    //gets card as a value and return it as a usable in spinner string. Return to listUtility
    public String cardToString(BankCard card){
        String proxy= card.getNumber()+ "   "+ card.getType();
        return proxy;
    }

    //gets a string in teh form of "cardnumber      type" and returs just the card number
    public String getCardNumber(String cardString){
        String proxy;
        String[] proxyList = cardString.split(" ");
        proxy = proxyList[0];
        return proxy;
    }

    //method for making a card number
    public String makeACardNumber(String type){
        String proxy="";
        if (type.equals("credit")){
            proxy=proxy+"1492";
        }else if(type.equals("debit")){
            proxy=proxy+"2992";
        }else if (type.equals("credit&debit")){
            proxy=proxy+"3284";
        }else{
            proxy=proxy+"5555";
        }
        proxy=makeFourDigitNumber()+makeFourDigitNumber()+makeFourDigitNumber();
        return proxy;
    }

    //method for making verification number
    public String makeVerification(String cardString){
        String proxy="";
        int IntProxy;
        proxy=makeFourDigitNumber();
        proxy=proxy.substring(0, proxy.length()-1);
        return proxy;
    }


    //following methods are being used in Allevents fragment to make a events string
}
