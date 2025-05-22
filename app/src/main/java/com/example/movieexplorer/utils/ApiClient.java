package com.example.movieexplorer.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class for creating and providing a Retrofit client instance.
 * Configured to connect to the TMDB API using Gson for JSON conversion.
 */
public class ApiClient {

    // Base URL for TMDB API
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    // Retrofit instance (singleton)
    private static Retrofit retrofit;

    /**
     * Returns a singleton Retrofit client instance.
     * If the instance doesn't exist, it initializes it with base URL and Gson converter.
     *
     * @return Retrofit client instance configured for TMDB API
     */
    public static Retrofit getClient() {
        // Create the Retrofit instance only once
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Set TMDB API base URL
                    .addConverterFactory(GsonConverterFactory.create()) // Use Gson to parse JSON
                    .build();
        }
        return retrofit;
    }
}
