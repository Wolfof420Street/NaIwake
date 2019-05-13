package com.wolf.na_iwake;

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
}
