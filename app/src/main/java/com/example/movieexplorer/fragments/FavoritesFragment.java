package com.example.movieexplorer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieexplorer.R;
import com.example.movieexplorer.adapters.MovieAdapter;
import com.example.movieexplorer.models.Movie;
import com.example.movieexplorer.utils.FavoritesManager;
import com.example.movieexplorer.utils.NetworkUtils;

import java.util.List;

/**
 * Fragment for displaying the user's favorite movies.
 * Shows a list of favorited movies and handles their display and removal.
 * Displays an empty state message when no favorites are present.
 */
public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;        // RecyclerView for displaying movies
    private MovieAdapter adapter;             // Adapter for the RecyclerView
    private View emptyStateContainer;         // Container for empty state message

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        // Check for internet connection
        if (!NetworkUtils.isConnected(getContext())) {
            showNoConnectionDialog();
            return inflater.inflate(R.layout.fragment_movies, container, false);
        }

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerView);
        emptyStateContainer = view.findViewById(R.id.emptyStateContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load and display favorite movies
        List<Movie> favorites = FavoritesManager.getFavorites(getContext());
        adapter = new MovieAdapter(favorites, getContext(), new MovieAdapter.OnFavoriteRemovedListener() {
            @Override
            public void onFavoriteRemoved() {
                updateEmptyState();
            }
        });
        recyclerView.setAdapter(adapter);
        updateEmptyState();

        return view;
    }

    /**
     * Updates the visibility of the empty state message based on whether there are any favorites
     */
    private void updateEmptyState() {
        if (adapter != null) {
            if (adapter.getItemCount() == 0) {
                emptyStateContainer.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyStateContainer.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            // Refresh the list of favorites when returning to this fragment
            List<Movie> refreshed = FavoritesManager.getFavorites(getContext());
            adapter.updateMovies(refreshed);
            updateEmptyState();
        }
    }

    /**
     * Shows a dialog when there is no internet connection
     * Provides options to retry or exit the app
     */
    private void showNoConnectionDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("Retry", (dialog, which) -> {
                    requireActivity().recreate();
                })
                .setNegativeButton("Exit", (dialog, which) -> {
                    requireActivity().finish();
                })
                .setCancelable(false)
                .show();
    }
}
