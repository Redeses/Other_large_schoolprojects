package com.example.harkkaty;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public SQLUtility testDB;

    // Edit text that are used to see what user wrote to username and password textboxes respectively
    protected EditText userN;
    protected EditText passW;
    private String  ID;
    public FrameLayout newUse;
    private FragmentManager manager;
    protected Fragment fragment;
    private addnfoView infoView;
    private SQLUtility sql;
    private StringUtility stringU;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userN = (EditText) findViewById(R.id.userName);
        passW = (EditText) findViewById(R.id.passwordChange);
        manager= getSupportFragmentManager();
        newUse = findViewById(R.id.newUser);
        newUse.bringToFront();
        newUse.setVisibility(View.INVISIBLE);
        sql= SQLUtility.getSQLUtil(this);
        sql = sql.getSQLUtil(MainActivity.this);
        newUse.setBackgroundColor(getResources().getColor(R.color.white));

    }


    public void logIn(View v){
        String username = String.valueOf(userN.getText());
        String password= String.valueOf(passW.getText());
        Cursor User;
        Boolean isUser;

        User = sql.logInCheck(username,password);
        if (User==null){
            return;
        }
        if(User.getCount()==0){
            Toast.makeText(this, "No user found", Toast.LENGTH_LONG).show();
            return;
        }
        User.moveToFirst();
        String passwordProxy=User.getString(User.getColumnIndex(SQLUtility.LogInCol2));

        if(passwordProxy.equals(password)){
            ID = User.getString(User.getColumnIndex(SQLUtility.LogInCol3));
            System.out.println(passwordProxy + ID);
            switchToMainUserView();
        }

    }

    //
    public void makeUser(View v){
        //newUse.setLayoutParams(new ConstraintLayout.LayoutParams(400,700));
        newUse.setVisibility(View.VISIBLE);
        fragment = new addnfoView();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.newUser, fragment);
        transaction.commit();

    }

    public void cancleInfo(){

        newUse.setVisibility(View.INVISIBLE);
    }

  /* @Override
   public void cancleInfo(){
       newUse.setVisibility(View.INVISIBLE);
   }*/


    protected void swtichToMasteView(){

    }


    protected void switchToMainUserView(){
        user = User.getCurrentUser();
        user.setUserID(ID);
        user.addCurrentUser(ID);
        Intent newIntent= new Intent(MainActivity.this, Home.class);
        newIntent.putExtra("ID", ID);
        this.finish();
        MainActivity.this.startActivity(newIntent);

    }
}
