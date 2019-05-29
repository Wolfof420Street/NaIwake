package com.wolf.na_iwake.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wolf.na_iwake.Constants;
import com.wolf.na_iwake.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    /*private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

*/


    private DatabaseReference mCocktailsReference;
    private ValueEventListener mCocktailsReferenceListener;

    @BindView(R.id.popularDrinksbutton) Button mpopularDrinksButton;
    @BindView(R.id.searchStoresbutton) Button msearchStoresButton;
    @BindView(R.id.textView)
    TextView mHomeTextView;
    @BindView(R.id.editCocktailText)
    EditText mCocktailEditText;
    @BindView(R.id.chatRoomButton) Button mChatRoomButton;
    @BindView(R.id.savedCocktailsButton) Button mSavedCocktailsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


       /* mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);*/

        /*mEditor = mSharedPreferences.edit();*/

        mCocktailsReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_COCKTAILS);


      mCocktailsReferenceListener= mCocktailsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot nameSnapshot : dataSnapshot.getChildren()) {
                    String name = nameSnapshot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/background.ttf");
        mHomeTextView.setTypeface(font);



        mSavedCocktailsButton.setOnClickListener(this);
        mpopularDrinksButton.setOnClickListener(this);
        msearchStoresButton.setOnClickListener(this);
        mChatRoomButton.setOnClickListener(this);

    }
        @Override
        public void onClick (View v) {
            if (v == mpopularDrinksButton) {
                String name = mCocktailEditText.getText().toString();

                saveCocktailToFireBase(name);
                /*if(!(name).equals("")) {
                    addToSharedPreferences(name);
                }*/
            Intent intent = new Intent(HomeActivity.this, CocktailsListActivity.class);

            intent.putExtra("Cocktail", name);
            startActivity(intent);
            }
            if (v == msearchStoresButton) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            } if (v == mSavedCocktailsButton) {
                Intent intent = new Intent(HomeActivity.this, SavedCocktailsActivity.class);
                startActivity(intent);
            }

            if (v == mChatRoomButton) {
                Intent intent = new Intent(HomeActivity.this, ChatRoomActivity.class);
                startActivity(intent);
            }
        }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCocktailsReference.removeEventListener(mCocktailsReferenceListener);
    }

    public void saveCocktailToFireBase(String name) {
        mCocktailsReference.push().setValue(name);
    }
   /* private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_DRINK_KEY, location).apply();
    }*/
    }

