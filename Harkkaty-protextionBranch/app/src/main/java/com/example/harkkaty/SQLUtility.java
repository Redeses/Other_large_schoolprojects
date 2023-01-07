package com.example.harkkaty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;

import java.util.ArrayList;
import java.util.Date;

public class SQLUtility extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="TheBigBank.db";
    //bank table Strings
    public static final String Table1_name= "Bank";
    public static final String BankCol1= "BankID";
    public static final String BankCol2 = "address";
    public static final String BankCol3= "name";
    // customer table strings
    public static final String Table2_name = "customer";
    public static final String CustomerCol1= "customerID";
    public static final String CustomerCol2= "firstName";
    public static final String CustomerCol3= "lastName";
    public static final String CustomerCol4= "address";
    public static final String CustomerCol5= "email";
    public static final String CustomerCol6= "country";
    public static final String CustomerCol7= "phoneNumber";
    public static final String CustomerCol8= "birthdate";
    public static final String CustomerCol9= "Bankid";
    //account table strings
    public static final String Table3_name = "account";
    public static final String AccountCol1= "accountID";
    public static final String AccountCol2= "customerid";
    public static final String AccountCol3= "currentBalance";
    public static final String AccountCol4= "savingsAccount";
    public static final String AccountCol5= "frozen";
    //admin table strings
    public static final String Table4_name= "admin";
    public static final String AdminCol1= "workerID";
    public static final String AdminCol2= "name";
    public static final String AdminCol3= "Bankid";
    //bankCard table strings
    public static final String Table5_name= "bankCard";
    public static final String BankCardCol1= "cardNumber";
    public static final String BankCardCol2= "pinCode";
    public static final String BankCardCol3= "verificationNumber";
    public static final String BankCardCol4= "type";
    public static final String BankCardCol5= "onlineLimit";
    public static final String BankCardCol6= "cashLimit";
    public static final String BankCardCol7= "cardLimit";
    public static final String BankCardCol8= "credit";
    public static final String BankCardCol9= "accountid";
    //logIn table srings
    public static final String Table6_name= "logIn";
    public static final String LogInCol1= "user";
    public static final String LogInCol2= "password";
    public static final String LogInCol3= "ID";
    public static final String LogInCol4= "admin";
    //AccountAdmin talbe strings
    public static final String Transition_table= "accountAdmin";
    public static final String AATableCol1= "accountid";
    public static final String AATableCol2= "workerid";
    //AccountEvent table Strings
    public static final String Table7_name = "accountevent";
    public static final String EventCol1="EventID";
    public static final String EventCol2="AccountID";
    public static final String EventCol3="date";
    public static final String EventCol4="receiver";
    public static final String EventCol5="message";
    public static final String EventCol6="amount";
    public static final String EventCol7="OtherAccount";


    private SQLiteDatabase sqlProxy;
    private static SQLUtility sqlU;
    ArrayList<String> info= new ArrayList<>();
    DateC datec=DateC.getDatec();

    private SQLUtility(Context context) {
        super(context, DATABASE_NAME, null, 1);
        makeList();

    }

    private void makeList(){
        for (int i=0; i<8; i++){
            info.add("");
        }
    }

    //may can be run on singleton
    public static SQLUtility getSQLUtil(Context context){
        if (sqlU == null){
            sqlU = new SQLUtility(context.getApplicationContext());
        }
        return sqlU;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createBankTable(db);
        createCustomerTable(db);
        createAccountTable(db);
        createAdminTable(db);
        createAccountAdmintTable(db);
        createBankCardTable(db);
        createLogInTable(db);
        createEventTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Table1_name);
        createBankTable(db);
    }



    private void createBankTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " +Table1_name+ "("+BankCol1+" varchar(25) PRIMARY KEY NOT NULL, "+BankCol2+" varchar(20), "+BankCol3+" varchar(25))");
    }

    private void createCustomerTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ Table2_name+ "("+CustomerCol1+" varchar(25) PRIMARY KEY NOT NULL," +
                CustomerCol2+" varchar(25)," + CustomerCol3+" varchar(25), " + CustomerCol4+" varchar(25), " +
                CustomerCol5+" varchar(25)," + CustomerCol6+" varchar(25)," +
                CustomerCol7+" varchar(25)," + CustomerCol8+" varchar(25)," +
                CustomerCol9+" varchar(25)," +

                "FOREIGN KEY("+CustomerCol9+") REFERENCES "+Table1_name+"("+BankCol1+")" +
                "ON DELETE CASCADE)");
    }

    private void createAccountTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ Table3_name+ "("+AccountCol1+" varchar(25) PRIMARY KEY NOT NULL," +
                AccountCol2+" varchar(25)," +
                ""+AccountCol3+" varchar(25)," +
                " "+AccountCol4+" bool, " +
                AccountCol5+" bool DEFAULT '0'," +
                "FOREIGN KEY ("+AccountCol2+") REFERENCES "+Table2_name+"("+CustomerCol1+") ON DELETE CASCADE )");
    }

    private void createAdminTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ Table4_name+ "("+AdminCol1+" varchar(25) PRIMARY KEY NOT NULL," +
                AdminCol2+" carchar(25), "+AdminCol3+" varchar(25), " +
                "FOREIGN KEY ("+AdminCol3+") REFERENCES "+Table1_name+"("+BankCol1+"))");
    }

    private void createAccountAdmintTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ Transition_table+"("+AATableCol1+" varchar(25), "+AATableCol2+" varchaar(25)," +
                "FOREIGN KEY ("+AATableCol1+") REFERENCES "+Table3_name+"("+AccountCol1+")," +
                "FOREIGN KEY ("+AATableCol2+") REFERENCES "+Table4_name+"("+AdminCol1+"))");
    }

    private void createBankCardTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ Table5_name +"("+BankCardCol1+" varchar(16) PRIMARY KEY NOT NULL," +
                BankCardCol2+" varchar(4), "+
                BankCardCol3+" varchar(3), "+
                BankCardCol4+" varchar(12)," +
                BankCardCol5+" INTEGER DEFAULT -1, "+
                BankCardCol6+" INTEGER DEFAULT -1, "+
                BankCardCol7+" INTEGER DEFAULT -1, "+
                BankCardCol8+" INTEGER DEFAULT 0," +
                BankCardCol9+" varchar(25), " +
                "CHECK (credit>-1)," +
                "FOREIGN KEY ("+BankCardCol9+") REFERENCES "+Table3_name+"("+AccountCol1+"))");
    }

    private void createLogInTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+Table6_name+"("+LogInCol1+" varchar(25) PRIMARY KEY NOT NULL," +
                LogInCol2+" varchar(30), "+LogInCol3+" carchar(25), "+LogInCol4+" bool default '0')");
    }


    private void createEventTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ Table7_name +"("+EventCol1 +" varchar(25) PRIMARY KEY NOT NULL,"
        + EventCol2+" varchar(25),"+ EventCol3+" varchar(30)," + EventCol4+" varchar(25),"+EventCol5+" varchar(25)," +
                EventCol6 + " varchar(30),"+ EventCol7+ " varcahr(30)," +
                "FOREIGN KEY("+EventCol2+") REFERENCES "+ Table3_name+"("+ AccountCol1+"))");
    }

    //adds login info to the LogIn sql table
    public boolean makeLogIn(String user, String password, String ID){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(LogInCol1, user);
            contentValues.put(LogInCol2, password);
            contentValues.put(LogInCol3, ID);
            long result= db.insert(Table6_name, null, contentValues);
            if (result ==-1){
                return false;
            }else {
                return true;
            }
        }catch (Exception e){
            return false;
        }

    }

    //checks if a usename exists in the database
    public boolean checkIfExists(String userName){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+Table6_name+" WHERE "+LogInCol1+" = "+userName, null);
            if (cursor.getCount()==0){
                cursor.close();
                return true;
            }else{
                cursor.close();
                return false;
            }
        }catch (Exception e){

        }
        return false;
    }
    //check if login info exists in the table and returns the cursor
    public Cursor logInCheck(String userName, String password){
        try{
            System.out.println(userName + password);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+Table6_name+" WHERE "+LogInCol1+" = '"+userName +
                    "' AND "+LogInCol2+" = '"+password+"'", null);
            return cursor;
        }catch (Exception e){
            return null;
        }
    }

    //add infromation of the new user to Customer table
    public void addInformation(String ID, String firstName, String lastName, String birthDate
            , String country, String address, String email, String phoneNumber){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            System.out.println(firstName+  lastName +  birthDate);
            contentValues.put(CustomerCol1, ID);
            contentValues.put(CustomerCol2, firstName);
            contentValues.put(CustomerCol3, lastName);
            contentValues.put(CustomerCol8, birthDate);
            contentValues.put(CustomerCol6, country);
            contentValues.put(CustomerCol4, address);
            contentValues.put(CustomerCol5, email);
            contentValues.put(CustomerCol7, phoneNumber);
            db.insert(Table2_name, null, contentValues);
            db.close();
        }catch (Exception e){

        }
    }

    //add completly new account
    public void addAccount(String AccountID, String customerID, boolean savings, String balanceString ){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(AccountCol1, AccountID);
            contentValues.put(AccountCol2, customerID);
            contentValues.put(AccountCol4, savings);
            contentValues.put(AccountCol3, balanceString);

            db.insert(Table3_name, null, contentValues);
            db.close();
        }catch (Exception e){

        }
    }


    //method for adding a new bankcard to database
    public void addBankcard(BankCard bankCard, String pincode, String verificationCode){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            System.out.println(bankCard.getCashLimit()+ " "+ bankCard.getCredit());
            ContentValues contentValues = new ContentValues();
            contentValues.put(BankCardCol1, bankCard.getNumber());
            contentValues.put(BankCardCol2, pincode);
            contentValues.put(BankCardCol3, verificationCode);
            contentValues.put(BankCardCol4, bankCard.getType());
            contentValues.put(BankCardCol5, bankCard.getOnlineLimit());
            contentValues.put(BankCardCol6, bankCard.getCashLimit() );
            contentValues.put(BankCardCol7, bankCard.getCheckingLimit());
            contentValues.put(BankCardCol8, bankCard.getCredit());
            contentValues.put(BankCardCol9, bankCard.getAccount());
            db.insert(Table5_name, null, contentValues);
            db.close();



        }catch (Exception e){

        }
    }


    public void addUserToBank(String id){

    }


    public void addEvent(AccountEvents accEvent){
        String proxy="";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventCol1, accEvent.getID());
        contentValues.put(EventCol2, accEvent.getAccountID());
        contentValues.put(EventCol3, accEvent.getDateString());
        contentValues.put(EventCol4, accEvent.getEntity());
        contentValues.put(EventCol5, accEvent.getmessage());
        contentValues.put(EventCol6, accEvent.getAmount());
        contentValues.put(EventCol7, accEvent.getReceivingAccount());
        db.insert(Table7_name, null, contentValues);
        db.close();
    }


    public void updateLimits(BankCard bankCard){
        int onlineLimit, cashLimit, checkingLimit, credit;
        onlineLimit = bankCard.getOnlineLimit();
        cashLimit = bankCard.getCashLimit();
        checkingLimit = bankCard.getCheckingLimit();
        credit = bankCard.getCredit();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String where= BankCardCol1+" = '"+bankCard.getNumber()+"'";
        String [] proxy = new String[] {bankCard.getNumber()};
        cv.put(BankCardCol5, onlineLimit);
        cv.put(BankCardCol6, cashLimit);
        cv.put(BankCardCol7, checkingLimit);
        cv.put(BankCardCol8,  credit);
        db.update(Table5_name, cv, where, null);
        db.needUpgrade(2);
        db.close();


    }

    //gets all UserID matching accounts from the database
    public ArrayList<Account> getAccounts(String userID){
        try{
            ArrayList<Account> accountsList = new ArrayList<>();
            Account acc;
            String accountID, balance;
            int savings;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+Table3_name+" WHERE "+AccountCol2+" = '"+userID+"'", null);
            if(cursor.getCount()==0) {
                return null;
            }
            cursor.moveToFirst();
            for(int i = 0; i<cursor.getCount(); i++){
                acc =new Account();
                accountID = cursor.getString(cursor.getColumnIndex(AccountCol1));
                balance = cursor.getString(cursor.getColumnIndex(AccountCol3));
                savings = cursor.getInt(cursor.getColumnIndex(AccountCol4));
                acc.setAccount(accountID,savings,balance);
                accountsList.add(acc);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            return accountsList;

        }catch (Exception e){
            return null;
        }
    }

    //gets all the cards tied to and account and their general info, not including verification number and pin code
    public ArrayList<BankCard> getCards(String accountID){
        try{
            ArrayList<BankCard> bankCardList = new ArrayList<>();
            BankCard Bacc;
            String cardNumber, type;
            int onlineL, cashL, checkingL, credit;
            int savings;

            SQLiteDatabase db = this.getWritableDatabase();
            System.out.println(Table5_name+" "+ accountID);
            Cursor cursor = db.rawQuery("SELECT * FROM "+Table5_name+" WHERE "+BankCardCol9+" = '"+accountID+"'", null);
            if(cursor.getCount()==0) {
                return null;
            }
            cursor.moveToFirst();
            for(int i = 0; i<cursor.getCount(); i++){
                Bacc =new BankCard();
                cardNumber = cursor.getString(cursor.getColumnIndex(BankCardCol1));
                type = cursor.getString(cursor.getColumnIndex(BankCardCol4));
                onlineL = cursor.getInt(cursor.getColumnIndex(BankCardCol5));
                cashL = cursor.getInt(cursor.getColumnIndex(BankCardCol6));
                checkingL = cursor.getInt(cursor.getColumnIndex(BankCardCol7));
                credit = cursor.getInt(cursor.getColumnIndex(BankCardCol8));
                Bacc.setBankCard(cardNumber, type, onlineL, cashL, checkingL, credit, accountID);
                bankCardList.add(Bacc);
                cursor.moveToNext();
            }
            db.close();
            cursor.close();
            return bankCardList;

        }catch (Exception e){
            return null;
        }
    }

    //the order the info lists info is name(full), birthdate, country, address, email, phonenumber, userName
    public ArrayList<String> getProfileInfo(String ID){
       try {
           makeList();
           String proxy, second;
           SQLiteDatabase db = this.getReadableDatabase();
           //cursor that searches for the ID's information from the customer atble
           Cursor cursor = db.rawQuery("SELECT * FROM "+Table2_name+ " WHERE "+ CustomerCol1 +"= '"+ ID+"'", null);
           cursor.moveToFirst();
           proxy= cursor.getString(cursor.getColumnIndex(CustomerCol2));
           second = cursor.getString(cursor.getColumnIndex(CustomerCol3));
           info.set(0, proxy+"."+second);

           proxy = cursor.getString(cursor.getColumnIndex(CustomerCol8));
           info.set(1,proxy);
           proxy= cursor.getString(cursor.getColumnIndex(CustomerCol6));
           info.set(2, proxy);
           proxy= cursor.getString(cursor.getColumnIndex(CustomerCol4));
           info.set(3, proxy);
           proxy= cursor.getString(cursor.getColumnIndex(CustomerCol5));
           info.set(4, proxy);
           proxy= cursor.getString(cursor.getColumnIndex(CustomerCol7));
           info.set(5, proxy);
           cursor = db.rawQuery("SELECT * FROM "+Table6_name+ " WHERE "+ LogInCol3 +"= '"+ ID+"'", null);
           cursor.moveToFirst();
           proxy= cursor.getString(cursor.getColumnIndex(LogInCol1));
           info.set(6, proxy);
           db.close();
           cursor.close();
           return info;
       }catch (Exception e){
            return info;
       }

    }

    //method for updating user information in the database. Information updated is country, address, email, phonenumber
    public void updateUserInfo(String country, String address, String email, String phoneN, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String where= CustomerCol1+" ='"+ id+"'";
        System.out.println(id);
        cv.put(CustomerCol6, country);
        cv.put(CustomerCol4, address);
        cv.put(CustomerCol5, email);
        cv.put(CustomerCol7,  phoneN);
        db.update(Table2_name, cv, where, null);
        db.needUpgrade(2);
        db.close();

    }
    /*
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cv = new ContentValues();
    String where= AccountCol1+" ='"+ accountID+"'";
    String [] proxy = new String[] {accountID};
        cv.put(AccountCol3, amount);
        db.update(Table3_name, cv, where, null);
        db.needUpgrade(2);
        db.close();
*/
    //check with user id if the password matches
    public boolean checkPassword(String ID, String password){
        try{
            boolean isInData = false;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM "+Table6_name+" WHERE "+LogInCol3+" = '"+ID+
                    "' AND "+LogInCol2+" = '"+password+"'", null);
            if (cursor.getCount()==0){

            }else{
                isInData = true;
            }
            db.close();
            return isInData;
        }catch (Exception e){
            return false;
        }
    }

    //method which changes the user password; used in password class
    public void updatePassword(String ID, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String where= LogInCol3+" = '"+ ID+"'";
        //String [] proxy = new String[] {ID};
        cv.put(LogInCol2, password);
        db.update(Table6_name, cv, where, null);
        db.needUpgrade(2);
        db.close();
    }

    //this is used when crrent accounts money is manipulated
    public void updateMoney(String accountID, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String where= AccountCol1+" ='"+ accountID+"'";
        String [] proxy = new String[] {accountID};
        cv.put(AccountCol3, amount);
        db.update(Table3_name, cv, where, null);
        db.needUpgrade(2);
        db.close();
    }

//Cursor cursor = db.rawQuery("SELECT * FROM "+Table3_name+" WHERE "+AccountCol2+" = "+userID, null);
    public void addMoney(String ID, double added){
        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();
        cursor= db.rawQuery("Select * FROM "+ Table3_name+ " WHERE " + AccountCol1 +" = '" + ID+"'",null);
        if(cursor.getCount()==0){
            return;
        }
        String proxyS = cursor.getString(cursor.getColumnIndex(AccountCol3));
        double proxyD = Double.parseDouble(proxyS);
        proxyD=proxyD + added;
        proxyS= Double.toString(proxyD);
        ContentValues cv = new ContentValues();
        String where= Table3_name+" = ?";
        String [] proxy = new String[] {ID};
        cv.put(AccountCol3, proxyS);
        db.update(Table2_name, cv, where, proxy);
        db.close();
    }

    //Date date, String Raccount, double Amount, String message, String entity, String id
    public ArrayList<AccountEvents> getEvents(String number){
        String date, Raccount, amount, message, entity, id;
        Date thedate;
        String DoubleProxy;
        double doubleProxy;
        SQLiteDatabase db = getReadableDatabase();
        AccountEvents accevent;
        ArrayList<AccountEvents> events=new ArrayList<AccountEvents>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table7_name+" WHERE "+EventCol2+" = '"+number+"'", null);
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); i++){
            accevent= new AccountEvents();
            date= cursor.getString(cursor.getColumnIndex(EventCol3));
            thedate = datec.makeIntoDate(date);
            Raccount = cursor.getString(cursor.getColumnIndex(EventCol7));
            amount = cursor.getString(cursor.getColumnIndex(EventCol6));
            message=cursor.getString(cursor.getColumnIndex(EventCol5));
            entity = cursor.getString(cursor.getColumnIndex(EventCol4));
            id = cursor.getString(cursor.getColumnIndex(EventCol2));
            if(amount==null){
                doubleProxy=0;
            }else{
                doubleProxy=Double.parseDouble(amount);
            }
            accevent.AccountEvents(thedate, Raccount, doubleProxy, message, entity, id);
            events.add(accevent);
            cursor.moveToNext();
        }
        return events;
    }

    //Date date, String Raccount, double Amount, String message, String entity, String id
    public void addEvent(Date date, String Raccount, double Amount, String message, String Entity, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String where= EventCol2+" = "+ Raccount;
        String [] proxy = new String[] {};
        cv.put(EventCol3, datec.getSimpleDate(date));
        cv.put(EventCol1, id);
        cv.put(EventCol2, Raccount);
        cv.put(EventCol4, Entity);
        cv.put(EventCol5, message);
        db.update(Table7_name, cv, where, proxy);
        db.close();
    }

    public void removeCard(BankCard card){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = BankCardCol1 +" = '"+ card.getNumber()+"'";
        db.delete(Table5_name, where, null);
        db.needUpgrade(2);
        db.close();

    }

}
