package com.example.harkkaty;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


//class for changin info on the profile
public class infoChange extends Fragment {
    private TextView name, birthdate, username;
    private EditText address, phone, email;
    private Spinner countrySpinner;
    private User us;
    private ArrayList<String> infoList;
    private StringUtility StringU;
    public Button finsih, cancle;

    private int spinnerPoint=0;
    private String spinnerSring, id;

    public infoChange() {
        StringU = StringUtility.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View infoChangeView = inflater.inflate(R.layout.fragment_change_info, container, false);
        //all of the text views have been change to include (I) in the end of their name
        us=User.getCurrentUser();
        name = (TextView) infoChangeView.findViewById(R.id.NameI);
        birthdate = (TextView) infoChangeView.findViewById(R.id.dateI);
        username = (TextView) infoChangeView.findViewById(R.id.userI);
        address = (EditText) infoChangeView.findViewById(R.id.adressI);
        phone = (EditText) infoChangeView.findViewById(R.id.phoneI);
        email = (EditText) infoChangeView.findViewById(R.id.emailI);
        countrySpinner = (Spinner) infoChangeView.findViewById(R.id.countries);
        finsih= (Button) infoChangeView.findViewById(R.id.finishInfo);
        cancle = (Button) infoChangeView.findViewById(R.id.cancel);
        setLister();
        Intent intent = new Intent();
        id = intent.getStringExtra("ID");
        setTextInView();
        setSpinner();
        return infoChangeView;
    }

    private void setLister(){
        finsih.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finishInfo();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cancelA();
            }
        });
    }

    public void cancelA(){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(infoChange.this);
        manager.popBackStack();
        transaction.commit();
    }

    public void finishInfo(){
        if(checkTexts()==false){
            Toast.makeText(getActivity(), "Fill in the missing fields", Toast.LENGTH_LONG).show();
            return;
        }else{
            updateList();
            us.updateUserInfo(infoList, id);
        }
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(infoChange.this);
        manager.popBackStack();
        transaction.commit();
    }

    //method Which updates infolist with new given info that is sent to USer class and the list there is updated
    public void updateList(){
        String proxy;
        proxy = address.getText().toString();
        infoList.set(3,proxy);
        proxy = email.getText().toString();
        infoList.set(4, proxy);
        proxy = phone.getText().toString();
        infoList.set(5, proxy);

    }

    //the order the info lists info is name(full), birthdate, country, address, email, phonenumber
    //the method set all ready text to the view
    private void setTextInView(){
        us= us.getCurrentUser();
        infoList = us.getAllInfo();
        String proxy= infoList.get(0).replace(".", " ");
        name.setText(proxy);
        birthdate.setText(infoList.get(1));
        spinnerSring = infoList.get(2);
        countrySpinner.setSelection(spinnerPoint);
        address.setText(infoList.get(3));
        email.setText(infoList.get(4));
        phone.setText(infoList.get(5));
        username.setText(infoList.get(6));

    }



    private void setSpinner(){
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSring=countrySpinner.getSelectedItem().toString();
                if ("None".equals(countrySpinner)){
                    return;
                }else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean checkTexts(){
        boolean textCheck = true;
        if(address.getText().toString().length()==0){
            Toast.makeText(getActivity(), "address field is empty", Toast.LENGTH_LONG).show();
            textCheck=false;
        }
        if(email.getText().toString().length()==0){
            Toast.makeText(getActivity(), "Email field is empty", Toast.LENGTH_LONG).show();
            textCheck=false;
        }
        if (StringU.checkforAtSign(email.getText().toString())==false){
            Toast.makeText(getActivity(), "Email field is empty", Toast.LENGTH_LONG).show();
            textCheck=false;
        }
        if(countrySpinner.getSelectedItem()=="None"){
            Toast.makeText(getActivity(), "No country selected", Toast.LENGTH_LONG).show();
            textCheck=false;
        }
        if(phone.getText().toString().length()==0){
            Toast.makeText(getActivity(), "Phone field empty", Toast.LENGTH_LONG).show();
            textCheck=false;
        }
        return textCheck;
    }

}
