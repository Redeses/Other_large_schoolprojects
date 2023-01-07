package com.example.harkkaty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CardInfo extends AppCompatActivity {
    private EditText ChekcingL, CashL, WebL;
    private TextView tv;
    private BankCard bc;
    private String bankcard, account;
    private Account acc;
    private User user;

    private String proxyString;
    private int proxyInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);
        Intent intent;
        user = User.getCurrentUser();
        bankcard = (String) getIntent().getStringExtra("BankCard");
        account = (String) getIntent().getStringExtra("Accountid");
        acc = user.getAccountByNumber(account);
        bc = acc.getACard(bankcard);
        ChekcingL = findViewById(R.id.chekinglimit);
        CashL = findViewById(R.id.cashLimit);
        WebL = findViewById(R.id.webL);
        tv = findViewById(R.id.CardNumber);
        setValues();
    }

    //sets all the infos of the activity
    private void setValues(){
        proxyString="";
        if(bc==null){
            back();
        }
        proxyString = bc.getNumber();
        tv.setText(proxyString);
        proxyInt = bc.getCheckingLimit();
        if(proxyInt==-1){
            proxyString="";
        }else{
            proxyString = Integer.toString(proxyInt);
        }

        ChekcingL.setText(proxyString);

        proxyInt = bc.getOnlineLimit();
        if(proxyInt==-1){
            proxyString="";
        }else{
            proxyString = Integer.toString(proxyInt);
        }

        System.out.println(proxyString);
        WebL.setText(proxyString);

        proxyInt = bc.getCashLimit();
        if(proxyInt==-1){
            proxyString="";
        }else{
            proxyString = Integer.toString(proxyInt);
        }
        CashL.setText(proxyString);

    }

    //is used for updating all the limits
    public void updateLimits(View v){
        proxyString = ChekcingL.getText().toString();
        if(proxyString.equals("")){
            proxyString="-1";
        }
        proxyInt = Integer.parseInt(proxyString);
        if (proxyInt<0){
            proxyInt=-1;
        }
        bc.setCheckingLimit(proxyInt);

        proxyString = CashL.getText().toString();
        if(proxyString.equals("")){
            proxyString="-1";
        }
        proxyInt = Integer.parseInt(proxyString);
        if (proxyInt<0){
            proxyInt=-1;
        }
        bc.setCashLimit(proxyInt);

        proxyString = WebL.getText().toString();
        if(proxyString.equals("")){
            proxyString="-1";
        }
        proxyInt = Integer.parseInt(proxyString);
        if (proxyInt<0){
            proxyInt=-1;
        }
        bc.setOnlineLimit(proxyInt);
        bc.setCredit(0);

        bc.updateSQL();
        setValues();
    }

    //deletes the card and returns to user cards
    public void deleteCard(View v){
        acc.removerCard(bc);
        Intent newIntent= new Intent(CardInfo.this, userCards.class);
        this.finish();
        CardInfo.this.startActivity(newIntent);

    }

    //goes to the previous place in the UI chain which is userCards
    public void back(View v){
        Intent newIntent= new Intent(CardInfo.this, userCards.class);
        this.finish();
        CardInfo.this.startActivity(newIntent);
    }

    public void back(){
        Intent newIntent= new Intent(CardInfo.this, userCards.class);
        this.finish();
        CardInfo.this.startActivity(newIntent);
    }

    //home button
    public void home(View v){
        Intent newIntent= new Intent(CardInfo.this, Home.class);
        this.finish();
        CardInfo.this.startActivity(newIntent);
    }

}
