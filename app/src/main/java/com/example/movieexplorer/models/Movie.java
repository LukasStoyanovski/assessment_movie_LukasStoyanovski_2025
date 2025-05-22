package com.example.movieexplorer.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Data model class representing a movie item.
 * This class maps JSON fields from the TMDB API to Java fields using Gson annotations.
 * Implements Serializable so it can be passed between activities via Intent.
 */
public class Movie implements Serializable {

    // Movie ID (not currently used but can be useful for future features)
    private int id;

    // Movie title
    private String title;

    // Movie poster image path from TMDB (mapped from "poster_path")
    @SerializedName("poster_path")
    private String posterPath;

    // Description of the movie
    private String overview;

    // Release date (mapped from "release_date")
    @SerializedName("release_date")
    private String releaseDate;

    // Average rating (mapped from "vote_average")
    @SerializedName("vote_average")
    private float rating;

    // Getter methods for accessing private fields

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public float getRating() {
        return rating;
    }
}
