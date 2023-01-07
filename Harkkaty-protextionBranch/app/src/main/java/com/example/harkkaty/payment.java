package com.example.harkkaty;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class payment extends Fragment {
    private EditText toAccount, receiver, amount, message, date;
    private Account AccountID;
    private Intent intent;
    private int DChecker=0, compareInt=0;
    private boolean isBackwards, DateisOK;
    private String generalProxy;

    private DateC datec;
    private User user;

    //date to be used when the payment event is created
    private Date paymentDate;
    public Button check;

    public payment() {
        DChecker = 0;
        compareInt = 0;
        isBackwards=false;
        DateisOK = false;
        user= User.getCurrentUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View paymentFragView=inflater.inflate(R.layout.fragment_payment, container, false);
        toAccount = paymentFragView.findViewById(R.id.paymentAccount);
        datec= DateC.getDatec();
        receiver = paymentFragView.findViewById(R.id.paymentReceiver);
        amount = paymentFragView.findViewById(R.id.paymentAmount);
        message = paymentFragView.findViewById(R.id.paymentMessage);
        date = paymentFragView.findViewById(R.id.paymentDate);
        check = paymentFragView.findViewById(R.id.paymentN);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment();
            }
        });
        setDatetextListener();
        return paymentFragView;
    }

    //used to set account from activitys spinner and resett the spinner
    public void setAcc(Account account) {
        AccountID= account;
    }

    //method taht checks if all relevant fields are filled and makes event log of it
    ///also updates the account that the money is sent to with the currency/if it exists
    public void makePayment(){
        AccountID = (Account) getArguments().getSerializable("account");
        String proyx=amount.getText().toString();
        if (proyx==""){

            return;
        }
        if (Double.parseDouble(proyx)<0){
            Toast.makeText(getActivity(), "Amount given can't be less than zero", Toast.LENGTH_LONG).show();
            return;
        }//checks if account has enough money to make transaction
        else if (Double.parseDouble(proyx)> AccountID.getBalance()){
            Toast.makeText(getActivity(), "Not enough money", Toast.LENGTH_LONG).show();
            return;
        }
        else if (toAccount.getText()==null){
            // toast
            return;
        }
        //next we check if the date is set that it is in the future
        if (DateisOK ==false){
            paymentDate =datec.currentDate();
        }else{
            paymentDate = datec.makeIntoDate(date.getText().toString());
        }
        //goes to account and adds the events to everywhere neccesary

        AccountID.addEvent(paymentDate, toAccount.getText().toString(), Double.parseDouble(proyx),
                message.getText().toString(), receiver.getText().toString());
        setTextToEmpty();
        AccountNewActivity activity = (AccountNewActivity) getActivity();
        activity.setSpinnet(user.getAccountNumberInSpinner(AccountID.getAccountNumber()));


    }

    private void setDatetextListener(){
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            //check if birthdate is at a point whre there is supposed to be a dot and adds it in
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                DChecker = date.getText().toString().length();
                if (DChecker<compareInt){
                    isBackwards=true;
                }else{
                    isBackwards=false;
                }
                DChecker = date.getText().toString().length();
                System.out.println(DChecker+ " "+isBackwards);

                if ((DChecker ==2)&&(isBackwards==false)){
                    generalProxy=date.getText().toString();
                    generalProxy = generalProxy +".";
                    date.setText(generalProxy);
                    date.setSelection(3);
                }else if((DChecker ==5)&&(isBackwards==false)){
                    generalProxy=date.getText().toString();
                    generalProxy = generalProxy+".";
                    date.setText(generalProxy);
                    date.setSelection(6);
                }

                generalProxy=date.getText().toString();
                if (DChecker == 10){
                    if((generalProxy.charAt(2)=='.' )&&(generalProxy.charAt(5)=='.')){
                        date.setTextColor(Color.GREEN);
                        return;
                    }
                    date.setTextColor(Color.GREEN);
                    DateisOK = true;
                } else{
                    date.setTextColor(Color.RED);
                    DateisOK = false;
                }
                compareInt=DChecker;
            }
        });
    }


    private void setTextToEmpty(){
        toAccount.setText("");
        receiver.setText("");
        amount.setText("");
        message.setText("");
        date.setText("");
    }
}
