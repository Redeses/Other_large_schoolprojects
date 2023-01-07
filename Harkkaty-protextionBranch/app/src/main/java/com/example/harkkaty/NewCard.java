package com.example.harkkaty;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


//fragment used in userCards activity to make new cards in the frmaeLayOut thats in it
public class NewCard extends Fragment {


    private EditText cheking, online, cash, credit;
    private String checkingString, onlineString, cashString, creditString;
    private Spinner spineer;
    private Account account;
    public Button makeCard;

    public NewCard() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View newCardFragView = inflater.inflate(R.layout.fragment_new_card, container, false);
        cheking = newCardFragView.findViewById(R.id.CheckingL);
        online = newCardFragView.findViewById(R.id.OnlineL);
        cash = newCardFragView.findViewById(R.id.CashL);
        credit = newCardFragView.findViewById(R.id.Credit);
        credit.setText("");
        spineer = newCardFragView.findViewById(R.id.chooseType);
        makeCard = newCardFragView.findViewById(R.id.makeCard);
        setTexts();
        setButtonListener();
        return newCardFragView;
    }


    private void setTexts(){
        cheking.setText("");
        online.setText("");
        credit.setText("");
        credit.setText("");
    }

    public void setButtonListener(){
        makeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNewCard();
            }
        });
    }

    public void makeNewCard(){
        checkingString=cheking.getText().toString();
        if(checkingString.equals("")){
            checkingString="-1";
        }
        onlineString= online.getText().toString();
        if(onlineString.equals("")){
            onlineString="-1";
        }
        cashString = cash.getText().toString();
        if(cashString.equals("")){
            cashString="-1";
        }
        creditString = credit.getText().toString();
        if(creditString.equals("")){
            creditString="0";
        }
        userCards activity = (userCards) getActivity();
        account=activity.getAccount();
        checkStrings();
        account.addCard(checkingString, onlineString, cashString, creditString, spineer.getSelectedItem().toString());
        ((userCards)getActivity()).hide();
    }

    //checks if the given values for limits are filled and if not they give the default -1
    private void checkStrings(){
        if(checkingString.equals("")){
            checkingString="-1";
        }
        if (onlineString.equals("")){
            onlineString="-1";
        }
        if (cashString.equals("")){
            cashString="-1";
        }
    }

}
