package com.wolf.na_iwake.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wolf.na_iwake.R;
import com.wolf.na_iwake.adapters.CocktailPagerAdapter;
import com.wolf.na_iwake.models.Cocktail;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CocktailDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private CocktailPagerAdapter adapterViewPager;
    ArrayList<Cocktail> mCocktails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_detail);
        ButterKnife.bind(this);

        mCocktails =  Parcels.unwrap(getIntent().getParcelableExtra("cocktails"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new CocktailPagerAdapter(getSupportFragmentManager(), mCocktails);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
        
    }

}
