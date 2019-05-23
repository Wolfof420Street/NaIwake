package com.wolf.na_iwake.models;

import org.parceler.Parcel;

@Parcel
public class Cocktail {
    private String mDrink;
    private String mDrinkThumb;
    private String mWebsite;


    public Cocktail () {}

    public Cocktail (String strDrink, String strDrinkThumb) {
        this.mDrink = strDrink;
        this.mDrinkThumb = strDrinkThumb;

    }
    public String getDrink () {
        return mDrink;
    }
    public String getDrinkThumb () {
        return mDrinkThumb;
    }
    public String getWebsite () { return mWebsite;}
    }


