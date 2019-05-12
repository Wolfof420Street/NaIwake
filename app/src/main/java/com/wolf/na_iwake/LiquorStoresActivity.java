package com.wolf.na_iwake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LiquorStoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liquor_stores);
        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
    }
}
