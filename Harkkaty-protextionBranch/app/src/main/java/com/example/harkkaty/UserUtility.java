package com.example.harkkaty;


import java.util.ArrayList;

//object to control everything user related from creatin new ones to changing information to linkin user with thei accounts
//aswell as checking if accounts are real
public class UserUtility {
    private personalInfoUtility piu;
    private ArrayList<String> allInfoList;
    private StringUtility SUtil;
    SQLUtility proxy;

    public UserUtility(){
        piu= personalInfoUtility.getInstance();
        allInfoList= piu.getAllInfoList();
        SUtil = StringUtility.getInstance();
        proxy=null;
    }

    public void makeAUser(String userN, String password, SQLUtility sqlU){
        proxy = sqlU;
        String ID = SUtil.makeID(userN);
        sqlU.makeLogIn(userN, password, ID);
        sqlU.addInformation(ID, allInfoList.get(0), allInfoList.get(1), allInfoList.get(2), allInfoList.get(3),
                allInfoList.get(4), allInfoList.get(5), allInfoList.get(6));
        sqlU.addUserToBank(ID);
    }

    //this method changes certain info in the user xml file
    public void changeUserInfo(){

    }
}
