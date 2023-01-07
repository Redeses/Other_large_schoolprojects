package com.example.harkkaty;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class password extends Fragment {

    private EditText password, newPassword, ReNewPassword;
    private String newpassword, ID;
    private SQLUtility sql;
    public Button back, change;


    public password() {
        sql= sql.getSQLUtil(this.getContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View passwordFragView = inflater.inflate(R.layout.fragment_password, container, false);
        password = passwordFragView.findViewById(R.id.oldP);
        newPassword = passwordFragView.findViewById(R.id.newP);
        ReNewPassword = passwordFragView.findViewById(R.id.reNewP);
        change = (Button) passwordFragView.findViewById(R.id.confirm);
        back = (Button) passwordFragView.findViewById(R.id.backStep);
        setButtonListeners();
        Intent intent = new Intent();
        ID= intent.getStringExtra("ID");
        return passwordFragView;
    }

    private void setButtonListeners(){
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    public void goBack(){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(password.this);
        manager.popBackStack();
        transaction.commit();
    }

    public void changePassword(){
        if (checkTexts()==false){
            return;
        }else if(!newPassword.getText().toString().equals(ReNewPassword.getText().toString())){
            Toast.makeText(getActivity(), "New passwords have to match", Toast.LENGTH_LONG).show();
            return;
        }else if(sql.checkPassword(ID, newPassword.toString().toString())){
            Toast.makeText(getActivity(), "New password can't be old password", Toast.LENGTH_LONG).show();
            return;
        }
        //updates the password in SQLutility class
        sql.updatePassword(ID, newPassword.getText().toString());
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(password.this);
        manager.popBackStack();
        transaction.commit();
    }

    private boolean checkTexts(){
        if(password.getText().toString()==""){
            return false;
        }else if (password.getText().toString()==""){
            return false;
        }else if (ReNewPassword.getText().toString()==""){
            return false;
        }

        return true;
    }

}
