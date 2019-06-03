package com.wolf.na_iwake.models;

import org.parceler.Parcel;

@Parcel
public class Cocktail {
   String mDrink;
   String mDrinkThumb;
   String mWebsite;
   String pushId;
    String index;


    public Cocktail () {}

    public Cocktail (String strDrink, String strDrinkThumb) {
        this.mDrink = strDrink;
        this.mDrinkThumb = strDrinkThumb;
        this.index = "not_specified";

    }
    public String getDrink () {
        return mDrink;
    }
    public String getDrinkThumb () {
        return mDrinkThumb;
    }
    public String getWebsite () { return mWebsite;}
    public String getPushId() {
        return pushId;
    }
    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
    }


