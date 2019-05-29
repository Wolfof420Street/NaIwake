package com.wolf.na_iwake.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wolf.na_iwake.R;

public class FirstActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent firstIntent = new Intent(FirstActivity.this, HomeActivity.class);
                startActivity(firstIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
