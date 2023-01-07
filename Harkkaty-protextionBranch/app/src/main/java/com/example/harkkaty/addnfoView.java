package com.example.harkkaty;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class addnfoView extends Fragment{
    View fragInfoView;
    EditText emailE, phone, firstName, lastName, address, birthDate;
    //String for adding all the personal info
    String allInfo, emailProxy, countryProxy, firstNameProxy, lastNameProxy, addressProxy, phoneProxy, generalProxy;
    //boolean value for checking if all text are okay to send
    boolean canSend, isBackwards;
    //int value to be used in checking the birthDate box, second is one used to compare to previous text size
    //to figure out wheather text is being removed
    int bDSChecker, compareInt;

    Spinner countries;

    public Button bacc, forward;
    public Fragment fragFrag;

    private personalInfoUtility PersonUtil = personalInfoUtility.getInstance();


    public addnfoView() {
        allInfo="";
        canSend=false;
        bDSChecker=0;
        compareInt=0;
        isBackwards=false;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragInfoView = inflater.inflate(R.layout.fragment_addnfo_view, container, false);
        bacc = (Button) fragInfoView.findViewById(R.id.cancle);
        forward = (Button) fragInfoView.findViewById(R.id.cont);
        emailE = (EditText) fragInfoView.findViewById(R.id.emailI);
        phone = (EditText) fragInfoView.findViewById(R.id.phonennumber);
        firstName = (EditText) fragInfoView.findViewById(R.id.firstname);
        lastName = (EditText) fragInfoView.findViewById(R.id.lastname);
        address = (EditText) fragInfoView.findViewById(R.id.address);
        birthDate = (EditText) fragInfoView.findViewById(R.id.birthdate);

        countries = (Spinner) fragInfoView.findViewById(R.id.addressI);


        buttonSetter();
        textListeners();
        spinnerListener();
        return fragInfoView;
    }



    //Method for a button listeners
    private void buttonSetter(){
        final MainActivity activity = (MainActivity)getActivity();

        bacc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.detach(addnfoView.this);
                transaction.commit();
               // canceL();
                activity.cancleInfo();
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameProxy = firstName.getText().toString();
                lastNameProxy = lastName.getText().toString();
                phoneProxy = phone.getText().toString();
                addressProxy = address.getText().toString();
                //a method which adds rest of the field to the infolist
                PersonUtil.AddAllToList(firstNameProxy, lastNameProxy, phoneProxy, addressProxy);
                canSend=PersonUtil.checkInfo();
                if (canSend==false){
                    Toast.makeText(getActivity(), "Not every field is filled in", Toast.LENGTH_LONG).show();
                    redColorSetter();
                    return;
                }
                goToUserNamer();
            }
        });
    }

    public void textListeners(){
        emailE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                emailProxy=emailE.getText().toString();
                boolean emailBool=PersonUtil.checkEmail(emailProxy);
                if (emailBool==true){
                    PersonUtil.addToLists(emailProxy, 5);
                    emailE.setTextColor(Color.GREEN);
                }
                else {
                    PersonUtil.removeFromLists(5);
                    emailE.setTextColor(Color.BLACK);
                }

            }
        });

        birthDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            //check if birthdate is at a point whre there is supposed to be a dot and adds it in
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  bDSChecker = birthDate.getText().toString().length();
                if (bDSChecker<compareInt){
                    isBackwards=true;
                }else{
                    isBackwards=false;
                }
                bDSChecker = birthDate.getText().toString().length();

                if ((bDSChecker ==2)&&(isBackwards==false)){
                    generalProxy=birthDate.getText().toString();
                    generalProxy = generalProxy +".";
                    birthDate.setText(generalProxy);
                    birthDate.setSelection(3);
                }else if((bDSChecker ==5)&&(isBackwards==false)){
                    generalProxy=birthDate.getText().toString();
                    generalProxy = generalProxy+".";
                    birthDate.setText(generalProxy);
                    birthDate.setSelection(6);
                }

                generalProxy=birthDate.getText().toString();
                if (bDSChecker == 10){
                    PersonUtil.addToLists(generalProxy, 2);
                    birthDate.setTextColor(Color.GREEN);
                }else {
                    birthDate.setTextColor(Color.BLACK);
                    PersonUtil.removeFromLists(2);
                }

                compareInt=bDSChecker;
            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone.setTextColor(Color.BLACK);
            }
        });

        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                firstName.setTextColor(Color.BLACK);
            }
        });
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lastName.setTextColor(Color.BLACK);
            }
        });
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                address.setTextColor(Color.BLACK);
            }
        });

    }


    public void spinnerListener(){
        countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryProxy=countries.getSelectedItem().toString();
                if ("None".equals(countryProxy)){
                    return;
                }else {
                    PersonUtil.addToLists(countryProxy, 3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    // opens framgmen called tunnusluonti which is used for creating user
    public void goToUserNamer(){
        FragmentManager fragManager = getFragmentManager();
        FragmentTransaction fragtrans = fragManager.beginTransaction();
        fragFrag = new TunnusLuonti();
        fragtrans.addToBackStack("addnfoView");
        fragtrans.hide(addnfoView.this);
        fragtrans.replace(R.id.newUser, fragFrag);
        fragtrans.commit();
    }

    private void closeFragment(){

        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }


    //sets the backGorund colour of the different textboxes to red depending on whether user has but info on them
    private void redColorSetter(){
        if (PersonUtil.returnBool(0)==false){
            firstName.setTextColor(Color.RED);
            System.out.println(0);
        }
        if (PersonUtil.returnBool(1)==false){
            lastName.setTextColor(Color.RED);
            System.out.println(11);
        }
        if (PersonUtil.returnBool(2)==false){
            birthDate.setTextColor(Color.RED);
            System.out.println(2);
        }
        if (PersonUtil.returnBool(3)==false){
            countries.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.red, null));
            System.out.println(3);
        }
        if (PersonUtil.returnBool(4)==false){
            address.setTextColor(Color.RED);
            System.out.println(4);
        }
        if (PersonUtil.returnBool(5)==false){
            phone.setTextColor(Color.RED);
            System.out.println(5);
        }
        if (PersonUtil.returnBool(6)==false){
            emailE.setTextColor(Color.RED);
            System.out.println(6);
        }

    }

    /*
//cancleInfo canceLView;
 @Override
    public void onAttach(Context context){
        super.onAttach(context);
        canceLView = (cancleInfo) context;
    }

    public interface cancleInfo{

    }

    public void canceL(){
        MainActivity.cancleInfo();
    }

    @Override
    public void onViewCreated(@NonNull View view)
    */
}