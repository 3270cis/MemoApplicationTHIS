package com.example.memoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class MemoSplash extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_memo_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MemoSplash.this, MemoActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
        //comment

    }
}
