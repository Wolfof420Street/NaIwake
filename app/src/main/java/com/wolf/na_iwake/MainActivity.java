package com.wolf.na_iwake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button mFindLiquorStoresButton;
    private EditText mLocationEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationEditText = (EditText) findViewById(R.id.locationEditText);

        mFindLiquorStoresButton = (Button) findViewById(R.id.FindLiquorStoresButton);

        mFindLiquorStoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LiquorStoresActivity.class);
                String location = mLocationEditText.getText().toString();
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });

    }
}
