package com.example.memoapplication;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//just a comment
public class MemoEditActivity extends AppCompatActivity {

    private Memo currentMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);
        initSaveButton();
        initViewListButton();

        //this gets the memo and priority that the user selected from MemoListActivity
        Intent intent = getIntent();
        String memoMessage = intent.getStringExtra("memo");
        String priority = intent.getStringExtra("priority");

        //this automatically sets the memo message on the box so the user can edit it
        EditText theMemoMessage = (EditText) findViewById(R.id.memoMessageEdit);
        theMemoMessage.setText(memoMessage);
        theMemoMessage.setEnabled(true);

        //this automatically sets the priority of the memo
        RadioButton radioButtonLow=(RadioButton)findViewById(R.id.lowEdit);
        RadioButton radioButtonMedium=(RadioButton)findViewById(R.id.mediumEdit);
        RadioButton radioButtonHigh=(RadioButton)findViewById(R.id.highEdit);

        if(priority.equals("Low")){
            radioButtonLow.setChecked(true);
        }

        else if(priority.equals("Medium")) {
            radioButtonMedium.setChecked(true);
        }

        else if(priority.equals("High")){
            radioButtonHigh.setChecked(true);
        }



        //initMemoRadioSetting();

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            initMemo(extras.getString(currentMemo.getMemoMessage()));
//        }
//
//        else {
//            currentMemo = new Memo();
//        }

    }

//    public void initMemo(String id) {
//
//        MemoDataSource ds = new MemoDataSource(MemoEditActivity.this);
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
//        EditText editMemoMessage = (EditText) findViewById(R.id.memoMessage);
//        editMemoMessage.setText(currentMemo.getMemoMessage());
//
//
//    }

    public void initViewListButton() {

        Button viewMemoButton = (Button) findViewById(R.id.viewMemoButtonEdit);

        viewMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MemoEditActivity.this, MemoListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }

    //FIX US
    public void initSaveButton() {

        Button saveButton = (Button) findViewById(R.id.saveButtonEdit);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String radioValue = getRadioValue();
                String memoMessage = getMemoMessage();

                currentMemo = new Memo(memoMessage,radioValue);

                //comment

                MemoDataSource ds = new MemoDataSource(MemoEditActivity.this);

                //this inserts the current memo object into the database
                try {
                    ds.open();
                    ds.insertMemo(currentMemo);
                    ds.close();
                    Toast.makeText(MemoEditActivity.this, "Updated into Memo!", Toast.LENGTH_LONG).show();


                } catch (Exception ex) {
                    Toast.makeText(MemoEditActivity.this, "something went wrong in the  memo DB", Toast.LENGTH_LONG).show();

                }


            }
        });

    }



    public String getMemoMessage() {

        EditText memoMessage = (EditText) findViewById(R.id.memoMessageEdit);

        return memoMessage.getText().toString();

    }

    public String getRadioValue() {

        // RadioButton radioButtonLow=(RadioButton)findViewById(R.id.low);
        RadioButton radioButtonMedium=(RadioButton)findViewById(R.id.mediumEdit);
        RadioButton radioButtonHigh=(RadioButton)findViewById(R.id.highEdit);



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
