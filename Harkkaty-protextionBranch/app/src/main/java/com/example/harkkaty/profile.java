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
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
//Fragemnt for changing user information
public class profile extends Fragment {
    private TextView name, birthdate, address, phone, email, username;
    private Intent intent;
    private User us;

    private ArrayList<String> allnfolist;

    private SQLUtility sql;
    private Fragment infoFrag;
    private String countryProxy;
    public Button toInfo, toPassword;
    private Spinner countries;

    public profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFrag = inflater.inflate(R.layout.fragment_profile, container, false);
        name = (TextView) viewFrag.findViewById(R.id.NameI);
        birthdate= (TextView) viewFrag.findViewById(R.id.dateI);
        address = (TextView) viewFrag.findViewById(R.id.addressI);
        phone = (TextView) viewFrag.findViewById(R.id.phoneI);
        email = (TextView) viewFrag.findViewById(R.id.emailI);
        username= (TextView) viewFrag.findViewById(R.id.userI);
        toInfo = (Button) viewFrag.findViewById(R.id.infoChange);
        toPassword = (Button) viewFrag.findViewById(R.id.passwordChange);
        sql=sql.getSQLUtil(this.getContext());
        setlistener();
        us = User.getCurrentUser();
        getInfo();
        return viewFrag;
    }

    @Override
    public void onResume(){
        super.onResume();
        getInfo();
    }

    public void setlistener(){
        toInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeInfo();
            }
        });
        toPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    public void getInfo(){
        us=us.getCurrentUser();
        Intent intent = new Intent();

        String ID = intent.getStringExtra("ID");

        //the order the info lists info is name(full), birthdate, country, address, email, phonenumber
        allnfolist=us.getAllInfo();
        name.setText(allnfolist.get(0));
        birthdate.setText(allnfolist.get(1));
        String proxy;
        proxy =allnfolist.get(3)+ " "+ allnfolist.get(2);
        address.setText(proxy);
        email.setText(allnfolist.get(4));
        phone.setText(allnfolist.get(5));
        username.setText(allnfolist.get(6));

    }


    public void changePassword(){
        FragmentManager fragManager = getFragmentManager();
        FragmentTransaction fragtrans = fragManager.beginTransaction();
        infoFrag = new password();
        fragtrans.addToBackStack("profile");
        fragtrans.hide(profile.this);
        fragtrans.replace(R.id.profileSettings, infoFrag);
        fragtrans.commit();
    }

    public void changeInfo(){
        FragmentManager fragManager = getFragmentManager();
        FragmentTransaction fragtrans = fragManager.beginTransaction();
        infoFrag = new infoChange();
        fragtrans.addToBackStack("profile");
        fragtrans.hide(profile.this);
        fragtrans.replace(R.id.profileSettings, infoFrag);
        fragtrans.commit();

    }

}
