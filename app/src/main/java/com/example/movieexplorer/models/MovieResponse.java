package com.example.movieexplorer.models;

import java.util.List;

/**
 * Model class representing the structure of the TMDB API response.
 * The API returns a JSON object with a "results" field containing a list of movies.
 * This class is used by Retrofit to deserialize that response.
 */
public class MovieResponse {

    // List of movies returned in the "results" field of the JSON response
    private List<Movie> results;

    /**
     * Getter for the list of movies.
     *
     * @return List of Movie objects
     */
    public List<Movie> getResults() {
        return results;
    }
}
