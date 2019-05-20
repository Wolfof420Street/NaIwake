package com.wolf.na_iwake.services;

import com.wolf.na_iwake.models.Cocktail;
import com.wolf.na_iwake.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CocktailService {
    public static void findCocktails(String name, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.COCKTAIL_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.COCKTAIL_NAME_QUERY_PARAMETER, name);

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Constants.COCKTAIL_TOKEN)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public ArrayList<Cocktail> processResults(Response response) {
        ArrayList<Cocktail> cocktails = new ArrayList<>();
        try {
            String xmlData = response.body().string();
            JSONObject cocktailJSON = new JSONObject(xmlData);
            JSONArray drinksJSON = cocktailJSON.getJSONArray("drinks");
            if (response.isSuccessful()) {
                for (int i = 0; i < drinksJSON.length(); i++) {
                    JSONObject cocktailsJSON = drinksJSON.getJSONObject(i);
                    String drink = cocktailsJSON.getString("strDrink");
                    String drinkThumb = cocktailsJSON.getString("strDrinkThumb");

                    Cocktail cocktail = new Cocktail(drink, drinkThumb);
                    cocktails.add(cocktail);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cocktails;
    }
}

