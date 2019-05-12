package com.wolf.na_iwake;

import android.content.Context;
import android.widget.ArrayAdapter;

public class LiquorStoreArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String [] mLiquorStores;
    private String [] mDrinks;

    public LiquorStoreArrayAdapter(Context mContext, int resource,  String [] mLiquorStores, String [] mDrinks) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mDrinks = mDrinks;
        this.mLiquorStores = mLiquorStores;
    }
    @Override
    public Object getItem(int position) {
        String liquorStore = mLiquorStores[position];
        String drinks = mDrinks[position];
        return String.format("%s \nKnown For: %s", liquorStore, drinks);
    }
    @Override
    public int getCount() {
        return mLiquorStores.length;
    }
}
