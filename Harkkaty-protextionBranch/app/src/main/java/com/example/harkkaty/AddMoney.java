package com.example.harkkaty;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddMoney extends Fragment {

    private Account acc;
    private EditText moneyAmount;
    public Button transaction;
    public User user;

    public AddMoney() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cashFragView =inflater.inflate(R.layout.fragment_add_money, container, false);
        acc= (Account) getArguments().getSerializable("account");
        moneyAmount = cashFragView.findViewById(R.id.addMoney);
        transaction = cashFragView.findViewById(R.id.addM);
        user = User.getCurrentUser();
        setListener();
        return cashFragView;
    }

    //used to set account from activitys spinner and resett the spinner
    public void setAcc(Account account){
        acc=account;
    }


    public void setListener(){
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTheMoney();
            }
        });
    }

    //adds money to the account
    public void addTheMoney(){
        String proxy = moneyAmount.getText().toString();
        if (proxy==""){
            Toast.makeText(this.getActivity(), "No money amount given", Toast.LENGTH_LONG).show();
            return;
        }
        acc.addMoney(Double.parseDouble(proxy));
        AccountNewActivity activity = (AccountNewActivity) getActivity();
        activity.setSpinnet(user.getAccountNumberInSpinner(acc.getAccountNumber()));

    }

}
