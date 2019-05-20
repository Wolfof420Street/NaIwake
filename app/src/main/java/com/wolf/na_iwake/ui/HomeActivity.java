package com.wolf.na_iwake.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wolf.na_iwake.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.popularDrinksbutton) Button mpopularDrinksButton;
    @BindView(R.id.searchStoresbutton) Button msearchStoresButton;
    @BindView(R.id.hometextView)
    TextView mHomeTextView;
    @BindView(R.id.editCocktailText)
    EditText mCocktailEditText;

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
                String name = mCocktailEditText.getText().toString();
            Intent intent = new Intent(HomeActivity.this, CocktailsListActivity.class);

            intent.putExtra("Cocktail", name);
            startActivity(intent);
            }
            if (v == msearchStoresButton) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            }
        }
    }

