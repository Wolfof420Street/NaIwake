package com.wolf.na_iwake.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wolf.na_iwake.Constants;
import com.wolf.na_iwake.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;


    @BindView(R.id.popularDrinksbutton) Button mpopularDrinksButton;
    @BindView(R.id.searchStoresbutton) Button msearchStoresButton;
    @BindView(R.id.hometextView)
    TextView mHomeTextView;
    @BindView(R.id.editCocktailText)
    EditText mCocktailEditText;
    @BindView(R.id.chatRoomButton) Button mChatRoomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mEditor = mSharedPreferences.edit();


        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/background.ttf");
        mHomeTextView.setTypeface(font);




        mpopularDrinksButton.setOnClickListener(this);
        msearchStoresButton.setOnClickListener(this);
        mChatRoomButton.setOnClickListener(this);

    }
        @Override
        public void onClick (View v) {
            if (v == mpopularDrinksButton) {
                String name = mCocktailEditText.getText().toString();
                if(!(name).equals("")) {
                    addToSharedPreferences(name);
                }
            Intent intent = new Intent(HomeActivity.this, CocktailsListActivity.class);

            intent.putExtra("Cocktail", name);
            startActivity(intent);
            }
            if (v == msearchStoresButton) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            }
            if (v == mChatRoomButton) {
                Intent intent = new Intent(HomeActivity.this, ChatRoomActivity.class);
                startActivity(intent);
            }
        }
    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_DRINK_KEY, location).apply();
    }
    }

