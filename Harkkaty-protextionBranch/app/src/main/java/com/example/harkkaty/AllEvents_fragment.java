package com.example.harkkaty;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class AllEvents_fragment extends Fragment {
    Button next, previous, leave;
    TextView Events;
    private StringUtility stringU;
    private ArrayList<AccountEvents> accEvents;
    private String accountI;
    private User user;
    private Account account;
    private AccountEvents accevent;
    private String eventString;

    private DateC dateU;

    //int used to check what the range of the page is and range is used to determine the size of the list
    private int pageLenght, range, proxy, lowerLimit, accSize;

    public AllEvents_fragment() {
        stringU = StringUtility.getInstance();
        pageLenght=10;
        range =0;
        eventString="";
        dateU=DateC.getDatec();
        user = User.getCurrentUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View eventFragView =inflater.inflate(R.layout.fragment_all_events_fragment, container, false);
        Events = eventFragView.findViewById(R.id.Allevents);
        next = eventFragView.findViewById(R.id.nextTab);
        previous = eventFragView.findViewById(R.id.previousTab);
        leave = eventFragView.findViewById(R.id.leave);

        lowerLimit=0;
        Bundle bundle =getArguments();
        accountI=bundle.getString("accountInfo");
        account = user.getSelectedAccount(accountI);
        accEvents = account.getTheEvents();
        //accsize is the constant size of the events arraylist
        accSize = accEvents.size();
        getRange();
        // proxy is the the int chekcing how many events are still left
        proxy = accEvents.size();
        user = User.getCurrentUser();
        setTextView();
        setOnClickListeners();

        return eventFragView;
    }

    private void setOnClickListeners(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AccountActivity)getActivity()).leaveEvents();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });
    }

    private void setTextView(){
        getRange();
        eventString="";
        for(int i=lowerLimit; i<range; i++){
            accevent = accEvents.get(i);
            eventString=eventString+accevent.getDateString() + " "+ accevent.getReceivingAccount()+" "+ accevent.getAmount()
            +" "+ accevent.getEntity()+ " "+ accevent.getmessage()+"\n";
        }
        Events.setText(eventString);

    }

    private void nextPage(){
        lowerLimit=lowerLimit+10;
        range = range+10;
        proxy = proxy-10;
        setTextView();
    }

    private void previousPage(){
        range=lowerLimit;
        lowerLimit=lowerLimit-10;
        proxy = proxy+10;
        setTextView();
    }

    //checks the current range of the page
    private void getRange(){
        if (proxy<=pageLenght){
            range = accSize;
            next.setVisibility(View.INVISIBLE);
        }else{
            next.setVisibility(View.VISIBLE);
        }
        if (lowerLimit==0){
            previous.setVisibility(View.INVISIBLE);
        }else{
            previous.setVisibility(View.VISIBLE);
        }
    }
}


