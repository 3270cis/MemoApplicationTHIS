package com.example.memoapplication;

import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Memo {

    private int memoID;
    private String memoMessage;
    private String priority;
    Calendar calendar;


    String dateToStr;



    public Memo(){

        memoID = 0;

        calendar = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");

        dateToStr = format.format(calendar.getTime());



    }

    //MIGHT NOT NEED THIS CONSTRUCTOR
//    public Memo(String newMemoMessage, String newPriority) {
//
//        this.memoMessage = newMemoMessage;
//        this.priority = newPriority;
//        dateOfMemo = new Date();
//
//    }

    public int getMemoID() {
        return memoID;
    }

    public void setMemoID(int memoID) {

        this.memoID = memoID;
    }


    public String getMemoMessage() {

        return memoMessage;
    }

    public void setMemoMessage(String memoMessage) {

        this.memoMessage = memoMessage;
    }

    public String getPriority() {

        return priority;
    }

    public void setPriority(String priority) {

        this.priority = priority;
    }

    public String getDateOfMemo() {

        return dateToStr;
    }

    public void setDateOfMemo(String dateToStr) {

        this.dateToStr = dateToStr;
    }


    public static void main(String [] args) {


    }
}
