package com.example.memoapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MemoActivity extends AppCompatActivity {


    private Memo currentMemo;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSaveButton();
        initViewListButton();
        //initMemoRadioSetting();

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            initMemo(extras.getInt("memoid"));
//        }
//
//        else {
//            currentMemo = new Memo();
//        }

    }

//    public void initMemo(int id) {
//
//        MemoDataSource ds = new MemoDataSource(MemoActivity.this);
//        try {
//            ds.open();
//            currentMemo = ds.getSpecificMemo(id);
//            ds.close();
//        } catch (Exception ex) {
//            Toast.makeText(this,"something went wrong in the initlize memo DB", Toast.LENGTH_LONG ).show();
//
//        }
//
//
////        EditText editMemoMessage = (EditText) findViewById(R.id.memoMessage);
////        editMemoMessage.setText(currentMemo.getMemoMessage());
//
//
//    }

    public void initViewListButton() {

        Button viewMemoButton = (Button) findViewById(R.id.viewMemoButton);

        viewMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MemoActivity.this, MemoListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }
//
//    private void initSaveButton() {
//        Button saveButton = (Button) findViewById(R.id.saveButton);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //hideKeyboard();
//                boolean wasSuccessful = false;
//
//
//                MemoDataSource ds = new MemoDataSource(MemoActivity.this);
//
//                try {
//                    ds.open();
//
//                    if (currentMemo.getMemoID() == -1) {
//
//                        String radioValue = getRadioValue();
//                        String memoMessage = getMemoMessage();
//
//
//                        currentMemo.setPriority(radioValue);
//                        currentMemo.setMemoMessage(memoMessage);
//
//                        wasSuccessful = ds.insertMemo(currentMemo);
//                        int newId = ds.getLastMemoId();
//                        currentMemo.setMemoID(newId);
//
//
//                    } else {
//                        //wasSuccessful = ds.updateContact(currentContact);
//                    }
//                    ds.close();
//                } catch (Exception e) {
//                    wasSuccessful = false;
//                    Toast.makeText(MemoActivity.this, "something went wrong in the initlize memo DB", Toast.LENGTH_LONG).show();
//
//                }
//
////                if (wasSuccessful) {
////                    ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
////                    editToggle.toggle();
////                    setForEditing(false);
////                }
//            }
//        });
//    }

    public void initSaveButton() {

        Button saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String radioValue = getRadioValue();
                String memoMessage = getMemoMessage();

                currentMemo = new Memo(memoMessage,radioValue);


                MemoDataSource ds = new MemoDataSource(MemoActivity.this);

                //this inserts the current memo object into the database
                try {
                    ds.open();
                    ds.insertMemo(currentMemo);
                    ds.close();
                    Toast.makeText(MemoActivity.this, "Entered into Memo!", Toast.LENGTH_LONG).show();


                } catch (Exception ex) {
                    Toast.makeText(MemoActivity.this, "something went wrong in the  memo DB", Toast.LENGTH_LONG).show();

                }


            }
        });

    }



    public String getMemoMessage() {

        EditText memoMessage = (EditText) findViewById(R.id.memoMessage);

        return memoMessage.getText().toString();

    }

    public String getRadioValue() {

        // RadioButton radioButtonLow=(RadioButton)findViewById(R.id.low);
        RadioButton radioButtonMedium=(RadioButton)findViewById(R.id.medium);
        RadioButton radioButtonHigh=(RadioButton)findViewById(R.id.high);



        if(radioButtonMedium.isChecked()){
            return "Medium";
        }

        else if(radioButtonHigh.isChecked()){
            return "High";

        }

        //the default return is Low, even if the user doesn't even specify the priority
        else {
            return "Low";
        }

    }


    //i dont think we need this

//    public void initMemoRadioSetting() {
//
//        RadioButton radioButtonLow=(RadioButton)findViewById(R.id.low);
//        RadioButton radioButtonMedium=(RadioButton)findViewById(R.id.medium);
//        RadioButton radioButtonHigh=(RadioButton)findViewById(R.id.high);
//
//        final RadioGroup radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
//
//        int priority=radioGroup.getCheckedRadioButtonId();
//
//
//        if(radioButtonLow.isChecked()){
//            radioButton=(RadioButton)findViewById(priority);
//            currentMemo.setPriority(radioButton.toString());
//        }
//
//        else if(radioButtonMedium.isChecked()){
//            radioButton=(RadioButton)findViewById(priority);
//            currentMemo.setPriority(radioButton.toString());
//        }
//
//        else if(radioButtonHigh.isChecked()){
//            radioButton=(RadioButton)findViewById(priority);
//            currentMemo.setPriority(radioButton.toString());
//        }
//
//        }



    }






