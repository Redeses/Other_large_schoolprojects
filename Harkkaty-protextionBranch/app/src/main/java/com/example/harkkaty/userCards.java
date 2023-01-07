package com.example.harkkaty;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class userCards extends AppCompatActivity {
    private User user;
    private ListUtility listU;
    private Spinner accounts;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recycAdapter;
    private RecyclerView.LayoutManager recycManager;
    private Account account;
    private int position;
    private ArrayList<BankCard> bankCardList;
    private StringUtility StringU;
    private int type;
    private FrameLayout newCardFrame;
    private Fragment fragment;
    private FragmentManager manager;
    private ArrayList<Account> accs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cards);
        user = user.getCurrentUser();
        listU= listU.getListUtility();
        accounts= findViewById(R.id.accountSpinner);
        recyclerView = findViewById(R.id.cardsRecycler);
        position=getIntent().getIntExtra("spinnePosition", 0);
        StringU = StringUtility.getInstance();
        newCardFrame = findViewById(R.id.makeNewCard);
        newCardFrame.setVisibility(View.INVISIBLE);
        newCardFrame.setBackgroundColor(getResources().getColor(R.color.white));
        manager = getSupportFragmentManager();
        accs = user.getAccounts();

        makeSpinner();

    }


    private void makeSpinner(){
        String[] str = new String[user.getAccountAmount()];
        //the proxy is used to find account selected

        str= listU.MakeAccountList(user.getAccountAmount());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, str);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accounts.setAdapter(adapter);
        accounts.setSelection(position);
        accounts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String proxy;
                proxy= accounts.getSelectedItem().toString();
                account=user.getSelectedAccount(proxy);
                setViewCards();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setViewCards(){
        bankCardList= account.getCards();
        String[] str = StringU.getCards(bankCardList);
        if(str.length==0){
            str=new String[]{""};
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycAdapter = new myAdapter(str, this);
        type = 2;
        ((myAdapter) recycAdapter).setType(type);
        ((myAdapter) recycAdapter).setContext(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recycAdapter);
        recyclerView.setLayoutManager(manager);


    }

    // moves to Card info activity
    public void ToCards(String cardnumber){
        Intent newIntent= new Intent(userCards.this, CardInfo.class);
        newIntent.putExtra("Accountid", account.getAccountNumber());
        newIntent.putExtra("BankCard", cardnumber);
        this.finish();
        userCards.this.startActivity(newIntent);
    }


    //method for opening a framelyaout where user can add a card
    public void addACard(View v){
        if (manager.getBackStackEntryCount()!=0){
            hide();
        }
        if (manager.getFragments().isEmpty()){
            newCardFrame.setVisibility(View.VISIBLE);
            fragment = new NewCard();
            newCardFrame.bringToFront();
            FragmentTransaction transaction = manager.beginTransaction();
            Intent intent = new Intent();
            transaction.replace(newCardFrame.getId(), fragment);
            transaction.commit();
        }
        else {
            hide();
        }


    }

    public void hide(){
        newCardFrame.setVisibility(View.INVISIBLE);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(fragment);
        transaction.commit();
        setViewCards();

    }


    public void home(View v){
        Intent newIntent= new Intent(userCards.this, Home.class);
        this.finish();
        userCards.this.startActivity(newIntent);
    }

    public Account getAccount(){
        return account;
    }
}
