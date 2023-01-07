package com.example.harkkaty;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class makeAccount extends Fragment {
    private EditText balance;
    private CheckBox savings;
    private User user;
    private Button make;
    public makeAccount() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View makeAccountFrag =inflater.inflate(R.layout.fragment_make_account, container, false);
        balance = (EditText) makeAccountFrag.findViewById(R.id.giveBalance);
        savings = makeAccountFrag.findViewById(R.id.isSavings);
        make = makeAccountFrag.findViewById(R.id.makeTheAccount);
        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAccount();
            }
        });
        user = user.getCurrentUser();
        return makeAccountFrag;
    }

    public void makeAccount(){
        boolean saving=false;
        if (savings.isChecked()){
            saving = true;
         }
        if (balance.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "No money available", Toast.LENGTH_LONG).show();
            return;
        }
        user.addAccount(saving, balance.getText().toString());
        user.setAccounts();
        Toast.makeText(getActivity(), "Account made", Toast.LENGTH_LONG).show();
        balance.setText("");
    }

}
