package com.example.teachhubproject.vue;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:9099/"; // Remplace localhost par l'IP publique si besoin
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Log.d("RetrofitClient", "Attempting to connect to BASE_URL: " + BASE_URL);
            try {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Log.d("RetrofitClient", "Retrofit initialized successfully.");
            } catch (Exception e) {
                Log.e("RetrofitClient", "Error initializing Retrofit", e);
            }

        }
        return retrofit;
    }
}
