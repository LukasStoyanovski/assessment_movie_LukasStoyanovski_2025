package com.example.movieexplorer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.movieexplorer.R;
import com.example.movieexplorer.models.Movie;

/**
 * This activity displays detailed information about a selected movie,
 * including title, poster, overview, release date, and rating.
 * It also provides a share button to recommend the movie.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    // UI components
    private ImageView poster;
    private TextView title, overview, releaseDate, rating;
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Bind layout views to Java variables
        poster = findViewById(R.id.detailPoster);
        title = findViewById(R.id.detailTitle);
        overview = findViewById(R.id.detailOverview);
        releaseDate = findViewById(R.id.detailReleaseDate);
        rating = findViewById(R.id.detailRating);
        shareButton = findViewById(R.id.detailShareButton);

        // Receive the Movie object passed via Intent
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        if (movie != null) {
            // Populate the views with movie details
            title.setText(movie.getTitle());
            overview.setText(movie.getOverview());
            releaseDate.setText("Release: " + movie.getReleaseDate());
            rating.setText("Rating: " + movie.getRating());

            // Load poster image using Glide
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .into(poster);

            // Set up share button to recommend movie via intent
            shareButton.setOnClickListener(v -> {
                String shareText = "Check out \"" + movie.getTitle() + "\" on MovieExplorer!";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            });
        }
    }
}
