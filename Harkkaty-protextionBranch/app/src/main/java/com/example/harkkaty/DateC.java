package com.example.harkkaty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateC {
    private Date date;
    private static DateC datec = new DateC();

    private DateC(){

    }

    public static DateC getDatec(){
        return datec;
    }

    //method which returns current tiem/date

    public Date currentDate() {
        date = new Date();
        return date;
    }


    //returns a day.month.year String of the given date
    public String getSimpleDate(Date theDate){
        String simpleDate="";
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        simpleDate = dateFormat.format(theDate);
        return simpleDate;
    }

    //method that gets dd.mm.yyyy format string and makes it into a date
    public Date makeIntoDate(String date){
        Date dateD=null;
        try {
            dateD = new SimpleDateFormat("dd.MM.yyyy").parse(date);
        }catch (ParseException e){

        }
        return dateD;
    }

    //method which comapares to dates it return a boolean where false means that the date is smaller then current
    //and true is when date is same or larger
    public boolean compareDates(String compareDate){
        Date date = makeIntoDate(compareDate);
        boolean returnValue;
        int dateProxyInt;
        dateProxyInt=currentDate().compareTo(date);
        if (dateProxyInt>0){
            returnValue = false;
        }else {
            returnValue = true;
        }
        return returnValue;
    }

}
