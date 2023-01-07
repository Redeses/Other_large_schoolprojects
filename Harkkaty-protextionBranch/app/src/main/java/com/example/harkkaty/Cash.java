package com.example.harkkaty;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Cash extends Fragment {
    private EditText cashAmount;
    private Spinner type, card;
    private String typeString, cardString;
    private String[] str;
    private User user;
    //button used for hiding payment
    private Button makePayment;

    private ListUtility listU;
    private DateC date;

    private Account proxyAccount;

    public Cash() {
        listU = listU.getListUtility();
        date = date.getDatec();
        user = User.getCurrentUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View cashFragView=inflater.inflate(R.layout.fragment_cash, container, false);
        cashAmount = cashFragView.findViewById(R.id.amountcash);
        type = cashFragView.findViewById(R.id.type);
        card = cashFragView.findViewById(R.id.cardSelected);
        makePayment = cashFragView.findViewById(R.id.makePayment);
        proxyAccount = (Account) getArguments().getSerializable("account");
        setSpinners();
        setAccountSpinner();
        return cashFragView;
    }

    //used to set account from activitys spinner and resett the spinner
    public void setAcc(Account account){
        proxyAccount=account;
        setAccountSpinner();
    }

    //sets the spinners when the fragment is created
    public void setSpinners() {
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeString = type.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment();
            }
        });
    }


    //sets the account spinner at the beginning of the run
     public void setAccountSpinner(){
        if (proxyAccount.getCardSize()==0){
            makePayment.setVisibility(View.INVISIBLE);
        }else {
            makePayment.setVisibility(View.VISIBLE);
        }
        str= listU.MakeCardList(proxyAccount);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, str);
        card.setAdapter(adapter);
        card.setSelection(0);
        card.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cardString = card.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //makes payment and and event
    public void makePayment(){
        String proxy=cashAmount.getText().toString();
        typeString = type.getSelectedItem().toString();
        if (proxy==""){
            Toast.makeText(getActivity(), "No money amout given", Toast.LENGTH_LONG).show();
            return;
        }
        double money = Double.parseDouble(proxy);
        if (money>proxyAccount.getBalance()){
            //toast here
            return;
        }

        Account AccountID = (Account) getArguments().getSerializable("account");
        proxyAccount.removeMoney(money);
        AccountNewActivity activity = (AccountNewActivity) getActivity();
        activity.setSpinnet(user.getAccountNumberInSpinner(AccountID.getAccountNumber()));
        String message = typeString+" transaction made from the card number: "+ cardString;
        proxyAccount.addEvent(date.currentDate(), typeString, money, message, typeString);

    }

}
