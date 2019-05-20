package com.wolf.na_iwake.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;


public class DrinksArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mVariety;
    private String[] mPrices;

    public DrinksArrayAdapter(Context mContext, int resource, String[] mVariety, String[] mPrices) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mPrices = mPrices;
        this.mVariety = mVariety;
    }

    @Override
    public Object getItem(int position) {
    String variety = mVariety[position];
    String price = mPrices [position];
    return String.format("%s \nPrice: %s", variety, price);
    }
    @Override
    public  int getCount () {
        return mVariety.length;
    }

    public static class LiquorStoreArrayAdapter extends ArrayAdapter {
        private Context mContext;
        private String [] mLiquorStores;
        private String [] mDrinks;

        public LiquorStoreArrayAdapter(Context mContext, int resource,  String [] mLiquorStores, String [] mDrinks) {
            super(mContext, resource);
            this.mContext = mContext;
            this.mLiquorStores = mLiquorStores;
            this.mDrinks = mDrinks;
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
}
