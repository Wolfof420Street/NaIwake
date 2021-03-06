package com.wolf.na_iwake.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wolf.na_iwake.R;
import com.wolf.na_iwake.adapters.DrinksArrayAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                LiquorStoresActivity extends AppCompatActivity {
    @BindView(R.id.locationTextView) TextView mLocationTextView;
    @BindView(R.id.listView) ListView mListView;

    private String[] liquorStores = new String[] {"Brew Bistro", "Waves",
            "Tribeka", "1824", "Milan", "Valhalla",
            "Charlie's", "Mwendas", "Switch", "Blend",
            "Edge", "Maggie's", "Red Velvet",
            "Chuom", "Whiskey Pitt"};
    private String[] drinks = new String[] {"Beer", "Vodka", "Gin", "Beer", "Rum", "Wine", "Brandy", "Scotch", "Cognac", "Tequilla", "Barley", "Cuban", "Bar Food", "Sports Bar", "Vermouth", "Alcohol" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liquor_stores);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        DrinksArrayAdapter.LiquorStoreArrayAdapter adapter = new DrinksArrayAdapter.LiquorStoreArrayAdapter(this, android.R.layout.simple_list_item_1, liquorStores, drinks);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String liquorStore = ((TextView)view).getText().toString();
                Intent intent1 = new Intent(LiquorStoresActivity.this, DrinksActivity.class);
                intent1.putExtra("Listviewclickvalue", liquorStore);
                startActivity(intent1);
                /*Toast.makeText(LiquorStoresActivity.this, liquorStore, Toast.LENGTH_LONG).show();*/
            }
        });





        mLocationTextView.setText("Here are all the liquor stores near: " + location);
    }
}
