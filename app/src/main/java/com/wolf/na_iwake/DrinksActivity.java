package com.wolf.na_iwake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;

public class DrinksActivity extends AppCompatActivity {
    private String[] variety = new String[] {"Jack Daniels", "Hunters", "Old Monk", "Kibao", "Blue Moon", "Gilbeys", "Johnny Walker", "Henessy", "Moon Walker", "Chrome", "Flirt", "Sky Vodka", "Absolute", "Afri Bull", "Bloody Mary", "Baileys" };
    private String[] prices = new String[] {"4500", "750", "650", "600", "400", "1800", "1800", "2500", "5000", "500", "1000", "600", "800", "750", "420", "690" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

    }
}
