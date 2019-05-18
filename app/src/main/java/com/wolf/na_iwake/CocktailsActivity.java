package com.wolf.na_iwake;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


public class CocktailsActivity extends AppCompatActivity {
    private static final String TAG = CocktailsActivity.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

  /*  @BindView(R.id.cocktailsListView)
    ListView mCocktailsListView;*/
  private  CockailListAdapter mAdapter;
    /*@BindView(R.id.findCocktailsButton)
    Button mCocktailsButton;*/

    public ArrayList<Cocktail> cocktails = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktails);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Cocktail");

        /*CocktailsArrayAdapter adapter  = new CocktailsArrayAdapter(this, android.R.layout.simple_list_item_1, cocktails);
        mCocktailsListView.setAdapter(adapter);*/

        getCocktails(name);
    }

    private void getCocktails(String name) {
        final CocktailService cocktailService = new CocktailService();
        cocktailService.findCocktails(name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                cocktails = cocktailService.processResults(response);

                CocktailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter = new CockailListAdapter(getApplicationContext(), cocktails);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CocktailsActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);

                        String[] cocktailNames = new String[cocktails.size()];
                        for (int i = 0; i < cocktailNames.length; i++) {
                            cocktailNames[i] = cocktails.get(i).getDrink();
                        }
                       /* ArrayAdapter adapter = new ArrayAdapter(CocktailsActivity.this, android.R.layout.simple_list_item_1, cocktailNames);
                        mCocktailsListView.setAdapter(adapter);*/
                        for (Cocktail cocktail : cocktails) {
                            Log.d(TAG, "Drink" + cocktail.getDrink());
                            Log.d(TAG, "Image" + cocktail.getDrinkThumb());
                        }
                    }
                });
            }
        });
    }
}



