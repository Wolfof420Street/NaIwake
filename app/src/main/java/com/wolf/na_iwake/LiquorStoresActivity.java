package com.wolf.na_iwake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LiquorStoresActivity extends AppCompatActivity {
    private TextView mLocationTextView;
    private String[] liquorStores = new String[] {"Brew Bistro", "Waves",
            "Tribeka", "1824", "Milan", "Valhalla",
            "Charlie's", "Mwendas", "Switch", "Blend",
            "Edge", "Maggie's", "Red Velvet",
            "Chuom", "Whiskey Pitt"};
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liquor_stores);
        mListView = (ListView) findViewById(R.id.listView);
        mLocationTextView = (TextView) findViewById(R.id.locationTextView);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, liquorStores);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String liquorStore = ((TextView)view).getText().toString();
                Toast.makeText(LiquorStoresActivity.this, liquorStore, Toast.LENGTH_LONG).show();
            }
        });


        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        mLocationTextView.setText("Here are all the liquor stores near: " + location);
    }
}
