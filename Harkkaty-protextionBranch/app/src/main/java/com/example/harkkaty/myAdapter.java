package com.example.harkkaty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyVIewHolder> {
    private String[] recycSrings;
    private int type;
    private View v;
    private LinearLayout linearL;
    private Activity acti;
    private Context cont;
    private String cardNumber;
    private LayoutInflater theInflater;

    private TextView textV;
    //sets the type of info beng used in the adapter 1 = its events and 2= bankcards
    public void setType (int type) {
        this.type = type;
    }

    //sets the activity that can be used later
    public void setActivity(Activity act){
        acti = act;
    }

    //set context from the give place
    public void setContext(Context context){
        cont = context;
    }

    private Button button;

    public static class MyVIewHolder extends RecyclerView.ViewHolder{
        public View textView;
        TextView theTextView;

        public MyVIewHolder (View v){
            super(v);
            textView = v;
            theTextView = v.findViewById(R.id.Recyc);
        }
    }

    public myAdapter(String[] recycData, Context context){
        this.theInflater = LayoutInflater.from(context);
        this.recycSrings = recycData;
    }

    @Override
    public MyVIewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        v = theInflater.inflate(R.layout.eventrecyc, parent, false);
        linearL = v.findViewById(R.id.touch);
        textV = v.findViewById(R.id.Recyc);
        return new MyVIewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyVIewHolder holder, int position){
        //add stuff

        String input = recycSrings[position];
        textV = v.findViewById(R.id.Recyc);
        holder.theTextView.setText(input);
        if(type ==2){
            setClickListener(holder);
        }
    }

    @Override
    public int getItemCount(){
        return  recycSrings.length;
    }


    //if the item is clicked when user is in userCards activity then it will move to activity known as
    private void setClickListener(final MyVIewHolder holder){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNumber = holder.theTextView.getText().toString();
                acti = (userCards) cont;
                ((userCards) acti).ToCards(cardNumber);
            }
        });
    }
}
