package com.example.harkkaty;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.harkkaty.SQLUtility.DATABASE_NAME;

public class SQL_Update extends SQLiteOpenHelper {
    private static SQLUtility SQL;
    private static SQL_Update cont;

    public SQL_Update(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQL = new SQLUtility(context);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //makes the login info to teh database, returns to makeUser method in personalutility/

}
