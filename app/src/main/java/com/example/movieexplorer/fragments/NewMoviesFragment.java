package com.example.movieexplorer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieexplorer.R;
import com.example.movieexplorer.adapters.MovieAdapter;
import com.example.movieexplorer.models.Movie;
import com.example.movieexplorer.models.MovieResponse;
import com.example.movieexplorer.utils.ApiClient;
import com.example.movieexplorer.utils.NetworkUtils;
import com.example.movieexplorer.utils.TMDBApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment for displaying new/now playing movies from TMDB API.
 * Fetches and displays a list of currently playing movies with their details.
 * Handles network connectivity and error states.
 */
public class NewMoviesFragment extends Fragment {

    private RecyclerView recyclerView;        // RecyclerView for displaying movies
    private MovieAdapter adapter;             // Adapter for the RecyclerView
    private List<Movie> currentMovies;        // Current list of movies being displayed

    private final String API_KEY = "16570cf3d38087d66cd5fe5676419385";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        // Check for internet connection
        if (!NetworkUtils.isConnected(getContext())) {
            showNoConnectionDialog();
            return inflater.inflate(R.layout.fragment_movies, container, false);
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch new movies
        fetchNewMovies();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentMovies != null) {
            // Refresh the adapter with the same movies to update favorite status
            adapter.updateMovies(currentMovies);
        }
    }

    /**
     * Fetches new/now playing movies from TMDB API
     * Updates the RecyclerView with the fetched movies
     */
    private void fetchNewMovies() {
        TMDBApi apiService = ApiClient.getClient().create(TMDBApi.class);
        Call<MovieResponse> call = apiService.getNowPlaying(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentMovies = response.body().getResults();
                    adapter = new MovieAdapter(currentMovies, getContext(), null);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Failed to load movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Shows a dialog when there is no internet connection
     * Provides options to retry or exit the app
     */
    private void showNoConnectionDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("Retry", (dialog, which) -> requireActivity().recreate())
                .setNegativeButton("Exit", (dialog, which) -> requireActivity().finish())
                .setCancelable(false)
                .show();
    }
}
