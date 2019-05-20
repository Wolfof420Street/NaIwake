package com.wolf.na_iwake.models;

public class Cocktail {
    private String mDrink;
    private String mDrinkThumb;

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
}
