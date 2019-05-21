package com.wolf.na_iwake.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wolf.na_iwake.models.Cocktail;
import com.wolf.na_iwake.ui.CocktailDetailFragment;

import java.util.ArrayList;

public class CocktailPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Cocktail> mCocktails;

    public CocktailPagerAdapter (FragmentManager fm, ArrayList <Cocktail> cocktails) {
        super(fm);
        mCocktails = cocktails;
    }
    @Override
    public Fragment getItem(int position) {
        return CocktailDetailFragment.newInstance(mCocktails.get(position));
    }
    @Override
    public int getCount () {
        return mCocktails.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mCocktails.get(position).getDrink();
    }
}
