package com.example.harkkaty;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

public class AccountNewActivity extends AppCompatActivity {
    private Spinner accountSpinner;
    private FrameLayout frameLayout;
    private Fragment fragment;
    private FragmentManager manager;
    private User user;
    private Account account;
    private ListUtility listU;
    //Intent intent;

    private Bundle bundle;

    private String proxy;

    //int that allows to keep chekc which fragment is active 1 = payment, 2= Cash, 3= AddMoney
    private int fragmentKeepr=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_new);
        frameLayout = findViewById(R.id.variableVIew);
        frameLayout.setBackgroundColor(Color.WHITE);
        accountSpinner = findViewById(R.id.AccountSpinner);
        frameLayout.setVisibility(View.INVISIBLE);
        manager = getSupportFragmentManager();
        user= user.getCurrentUser();
        listU= listU.getListUtility();
        setSpinnet(0);
    }

    //add a fragment int the framelayout. the fragment is closed by pressing the same button.
    //the fragment started is the payment fragment where user can make payments to accounts
    public void startPaymentFragment(View v){
        if(manager.getFragments().isEmpty()){
            frameLayout.setVisibility(View.VISIBLE);
            fragment = new payment();
            setBundle();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.variableVIew, fragment);
            transaction.commit();
            fragmentKeepr=1;
        }else if(fragmentKeepr==1) {
            frameLayout.setVisibility(View.INVISIBLE);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.detach(fragment);
            transaction.commit();
            fragmentKeepr=0;
        }
        else{
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.detach(fragment);
            fragment = new payment();
            setBundle();
            transaction.replace(R.id.variableVIew, fragment);
            transaction.commit();
            fragmentKeepr=1;
        }
    }

    //add a fragment int the framelayout. the fragment is closed by pressing the same button
    //starts cash fragment where user can take cash out from a virtual atm with a bank card
    public void startCashFragment(View v){
        if(manager.getFragments().isEmpty()){
            frameLayout.setVisibility(View.VISIBLE);
            fragment = new Cash();

            FragmentTransaction transaction = manager.beginTransaction();
            setBundle();
            transaction.replace(R.id.variableVIew, fragment);
            transaction.commit();
            fragmentKeepr=2;
        }else if(fragmentKeepr==2){
            frameLayout.setVisibility(View.INVISIBLE);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.detach(fragment);
            transaction.commit();
            fragmentKeepr=0;
        }
        else{

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.detach(fragment);
            fragment = new Cash();
            transaction.replace(R.id.variableVIew, fragment);
            setBundle();
            transaction.commit();
            fragmentKeepr=2;
        }
    }

    //add a fragment int the framelayout. the fragment is closed by pressing the same button
    // begins AddMoney fragment where there can be added money to the account given
    public void startAddMoneyFragment(View v){
        if(manager.getFragments().isEmpty()){
            frameLayout.setVisibility(View.VISIBLE);
            fragment = new AddMoney();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.variableVIew, fragment);
            setBundle();
            transaction.commit();
            fragmentKeepr=3;
        }else if(fragmentKeepr==3){
            frameLayout.setVisibility(View.INVISIBLE);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.detach(fragment);
            transaction.commit();
            fragmentKeepr=0;
        }
        else{
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.detach(fragment);
            fragment = new AddMoney();
            transaction.replace(R.id.variableVIew, fragment);
            setBundle();
            transaction.commit();
            fragmentKeepr=3;
        }
    }

    //just gets bundle arguments to be used in the fragments
    public void setBundle(){
        fragment.setArguments(bundle);
    }


    //sets spinner that are used in the fragments and their method
    public void setSpinnet(int select){
        String[] str = new String[user.getAccountAmount()];
        //the proxy is used to find account selected
        /*if (user.getAccountAmount()==0){
            frameLayout.setVisibility(View.INVISIBLE);
            return;
        }*/
        str= listU.MakeAccountList(user.getAccountAmount());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, str);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(adapter);
        accountSpinner.setSelection(select);
        proxy= accountSpinner.getSelectedItem().toString();
        account=user.getSelectedAccount(proxy);
        accountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                proxy= accountSpinner.getSelectedItem().toString();
                account=user.getSelectedAccount(proxy);
                //intent=new Intent();
                //intent.putExtra("accountID", account);

                bundle = new Bundle();
                bundle.putSerializable("account", account);
                if (fragmentKeepr ==1){
                    ((payment) fragment).setAcc(account);
                }else if (fragmentKeepr==2){
                    ((Cash) fragment).setAcc(account);
                }else if (fragmentKeepr==3){
                    ((AddMoney) fragment).setAcc(account);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //returns to Accountactivity
    public void goBack(View v){
        Intent newIntent= new Intent(AccountNewActivity.this, AccountActivity.class);
        AccountNewActivity.this.finish();
        AccountNewActivity.this.startActivity(newIntent);
    }


    //goes to Home activity
    public void goHome(View v){
        Intent newIntent= new Intent(AccountNewActivity.this, Home.class);
        AccountNewActivity.this.finish();
        AccountNewActivity.this.startActivity(newIntent);
    }

}
