package com.wolf.na_iwake;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.popularDrinksbutton) Button mpopularDrinksButton;
    @BindView(R.id.searchStoresbutton) Button msearchStoresButton;
    @BindView(R.id.hometextView)
    TextView mHomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/background.ttf");
        mHomeTextView.setTypeface(font);


        mpopularDrinksButton.setOnClickListener(this);
        msearchStoresButton.setOnClickListener(this);

    }
        @Override
        public void onClick (View v) {
            if (v == mpopularDrinksButton) {
            Intent intent = new Intent(HomeActivity.this, CocktailsActivity.class);
            startActivity(intent);
            }
            if (v == msearchStoresButton) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            }
        }
    }

