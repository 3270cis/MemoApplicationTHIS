package com.example.memoapplication;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Memo currentMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSaveButton();
        initViewListButton();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            initMemo(extras.getInt("memoid"));
        } else {
            currentMemo = new Memo();
        }
    }

    public void initMemo(int id) {
        ContactDataSource ds = new ContactDataSource(MainActivity.this);
        try {
            ds.open();
            currentMemo = ds.getSpecificMemo(id);
            ds.close();
        } catch (Exception ex) {
            Toast.makeText(this,"something went wrong in the initlize memo DB", Toast.LENGTH_LONG ).show();

        }

        EditText editMemoMessage = (EditText) findViewById(R.id.memo);


        editMemoMessage.setText(currentMemo.getMemoMessage());



    }

    public void initViewListButton() {

        Button viewMemoButton = (Button) findViewById(R.id.viewMemoButton);

        viewMemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MemoListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

    }

    public void initSaveButton(){

        Button saveButton =  (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        RadioButton radioButton;

                        RadioButton radioButtonLow = (RadioButton) findViewById(R.id.low);
                        RadioButton radioButtonMedium = (RadioButton) findViewById(R.id.medium);
                        RadioButton radioButtonHigh = (RadioButton) findViewById(R.id.high);


                        //the defualt is low if the users doesnt specificy the priortty.
                        int priority = radioGroup.getCheckedRadioButtonId();

                        if(radioButtonLow.isChecked()) {
                            radioButton = (RadioButton) findViewById(priority);
                            currentMemo.setPriority(radioButton.toString());
                        }

                        else if (radioButtonMedium.isChecked()) {
                            radioButton = (RadioButton) findViewById(priority);
                            currentMemo.setPriority(radioButton.toString());
                        }

                        else if (radioButtonHigh.isChecked()) {
                            radioButton = (RadioButton) findViewById(priority);
                            currentMemo.setPriority(radioButton.toString());
                        }

                        ContactDataSource ds = new ContactDataSource(MainActivity.this);

                        try {
                            ds.open();
                            ds.insertMemo(currentMemo);
                            ds.close();

                        } catch (Exception ex) {
                            Toast.makeText(MainActivity.this,"something went wrong in the initlize memo DB", Toast.LENGTH_LONG ).show();

                        }


                    }
                });


            }
        });



    }







}
