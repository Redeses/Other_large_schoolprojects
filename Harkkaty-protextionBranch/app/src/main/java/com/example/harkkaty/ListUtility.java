package com.example.harkkaty;

import java.util.ArrayList;

public class ListUtility {
    private static ListUtility util = new ListUtility();
    private User user;

    private StringUtility StringU;
    private ArrayList<Account> AccountProxy;
    private ArrayList<BankCard> cardProxy;

    private ListUtility(){
        user=user.getCurrentUser();
        StringU=StringUtility.getInstance();
    }

    public static ListUtility getListUtility(){
        return util;
    }

    //makes accounts information into a list that can be used while making a spinner in other method
    //currently used in AccountActivity
    public String[] MakeAccountList(int size){
        String[] str = new String[size];
        AccountProxy = user.getAccounts();
        for (int i=0; i<size; i++){
            str[i] = StringU.makeAccountToString(AccountProxy.get(i));
        }
        return str;
    }

    public String[] MakeCardList(Account account){
        String[] str = new String[account.getCardSize()];
        cardProxy = account.getCards();
        for (int i=0; i<account.getCardSize(); i++){
            str[i] = StringU.cardToString(cardProxy.get(i));
        }
        return str;
    }

}
