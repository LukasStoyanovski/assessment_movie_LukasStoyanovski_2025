package com.example.movieexplorer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieexplorer.R;
import com.example.movieexplorer.models.Movie;
import com.example.movieexplorer.utils.FavoritesManager;
import android.content.Intent;
import com.example.movieexplorer.activities.MovieDetailsActivity;

import java.util.List;

/**
 * Adapter class for displaying movies in a RecyclerView.
 * Handles the display of movie information including title, poster, and favorite status.
 * Manages favorite functionality and navigation to movie details.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;           // List of movies to display
    private Context context;              // Application context
    private OnFavoriteRemovedListener listener;  // Callback for favorite removal
    private boolean isFavoritesTab;       // Flag to identify if this adapter is used in Favorites tab

    /**
     * Interface for handling favorite removal events
     * Used to notify the FavoritesFragment when a movie is removed from favorites
     */
    public interface OnFavoriteRemovedListener {
        void onFavoriteRemoved();
    }

    /**
     * Constructor for MovieAdapter
     * @param movies List of movies to display
     * @param context Application context
     * @param listener Callback for favorite removal events
     */
    public MovieAdapter(List<Movie> movies, Context context, OnFavoriteRemovedListener listener) {
        this.movies = movies;
        this.context = context;
        this.listener = listener;
        this.isFavoritesTab = (listener != null);
    }

    /**
     * Updates the list of movies and refreshes the view
     * @param newMovies New list of movies to display
     */
    public void updateMovies(List<Movie> newMovies) {
        this.movies = newMovies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        // Set movie title
        holder.title.setText(movie.getTitle());

        // Set click listener for movie item to open details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("movie", movie);
            context.startActivity(intent);
        });

        // Load movie poster using Glide
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
            .into(holder.poster);

        // Set favorite status and icon
        boolean isFavorite = FavoritesManager.isFavorite(context, movie);
        holder.favoriteIcon.setImageResource(
                isFavorite ? R.drawable.ic_star_filled : R.drawable.ic_star_border
        );

        // Handle favorite icon click
        holder.favoriteIcon.setOnClickListener(v -> {
            if (FavoritesManager.isFavorite(context, movie)) {
                // Remove from favorites
                FavoritesManager.removeFavorite(context, movie);
                holder.favoriteIcon.setImageResource(R.drawable.ic_star_border);
                // Only remove from list if we're in the Favorites tab
                if (isFavoritesTab) {
                    movies.remove(position);
                    notifyItemRemoved(position);
                    if (listener != null) {
                        listener.onFavoriteRemoved();
                    }
                }
            } else {
                // Add to favorites
                FavoritesManager.saveFavorite(context, movie);
                holder.favoriteIcon.setImageResource(R.drawable.ic_star_filled);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /**
     * ViewHolder class for movie items
     * Holds references to the views in the item_movie layout
     */
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;      // Movie poster image
        TextView title;        // Movie title
        ImageView favoriteIcon; // Favorite status icon

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.moviePoster);
            title = itemView.findViewById(R.id.movieTitle);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
        }
    }
}
