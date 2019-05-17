package com.wolf.na_iwake;

import android.content.Context;
import android.widget.ArrayAdapter;

public class CocktailsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String [] mCocktails;

    public CocktailsArrayAdapter (Context mContext, int resource, String [] mCocktails) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mCocktails = mCocktails;

    }
    @Override
    public Object getItem(int position){
        String cocktails = mCocktails[position];
        return cocktails;
    }
    @Override
    public int getCount () {
        return mCocktails.length;
    }
}
