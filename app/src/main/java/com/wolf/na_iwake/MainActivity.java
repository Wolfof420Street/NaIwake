package com.wolf.na_iwake;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.FindLiquorStoresButton)
    Button mFindLiquorStoresButton;
    @BindView(R.id.locationEditText)
    EditText mLocationEditText;
    @BindView(R.id.NaIwaketextView)
    TextView mAppNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/background.ttf");
        mAppNameTextView.setTypeface(font);

        mFindLiquorStoresButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mFindLiquorStoresButton) {
            Intent intent = new Intent(MainActivity.this, LiquorStoresActivity.class);
            String location = mLocationEditText.getText().toString();
            intent.putExtra("location", location);
            startActivity(intent);
        }
    }
}


