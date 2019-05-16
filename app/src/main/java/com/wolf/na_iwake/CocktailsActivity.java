package com.wolf.na_iwake;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CocktailsActivity extends AppCompatActivity {
    private static final String TAG = CocktailsActivity.class.getSimpleName();
    @BindView(R.id.cocktailsTextView)
    TextView mCocktailsTextView;
    @BindView(R.id.findCocktailsButton)
    Button mCocktailsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktails);
        ButterKnife.bind(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "Fonts/background.ttf");
        mCocktailsTextView.setTypeface(font);

        Intent intent = getIntent();
        String name = "";

        mCocktailsTextView.setText("Here is The List of Cocktails" );
        getCocktails(name);
    }
    private void getCocktails (String name) {
        final CocktailService cocktailService = new CocktailService();
        cocktailService.findCocktails(name, new Callback () {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
