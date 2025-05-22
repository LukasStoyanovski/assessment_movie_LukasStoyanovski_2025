package com.example.movieexplorer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.movieexplorer.models.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for managing favorite movies using SharedPreferences.
 * Favorites are stored locally in JSON format using Gson.
 */
public class FavoritesManager {

    // Name of the SharedPreferences file
    private static final String PREF_NAME = "movie_favorites";

    // Key under which the favorite movies list is saved
    private static final String KEY_FAVORITES = "favorites";

    /**
     * Saves a movie to favorites if it's not already present.
     */
    public static void saveFavorite(Context context, Movie movie) {
        List<Movie> favorites = getFavorites(context);
        if (!favorites.contains(movie)) {
            favorites.add(movie);
            saveFavoritesList(context, favorites);
        }
    }

    /**
     * Removes a movie from the favorites list based on its title.
     */
    public static void removeFavorite(Context context, Movie movie) {
        List<Movie> favorites = getFavorites(context);
        favorites.removeIf(f -> f.getTitle().equals(movie.getTitle()));
        saveFavoritesList(context, favorites);
    }

    /**
     * Retrieves the list of favorite movies from SharedPreferences.
     * If no data is found, returns an empty list.
     */
    public static List<Movie> getFavorites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_FAVORITES, null);
        Type type = new TypeToken<List<Movie>>() {}.getType();
        return json == null ? new ArrayList<>() : new Gson().fromJson(json, type);
    }

    /**
     * Saves the provided list of favorite movies to SharedPreferences.
     */
    private static void saveFavoritesList(Context context, List<Movie> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_FAVORITES, new Gson().toJson(list)).apply();
    }

    /**
     * Checks if a movie is already marked as favorite.
     * Compares movies based on their title.
     */
    public static boolean isFavorite(Context context, Movie movie) {
        return getFavorites(context).stream().anyMatch(f -> f.getTitle().equals(movie.getTitle()));
    }
}
