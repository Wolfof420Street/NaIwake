package com.wolf.na_iwake;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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

}
