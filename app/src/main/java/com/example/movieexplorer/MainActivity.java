package com.example.movieexplorer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movieexplorer.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Main entry point of the app.
 * Hosts the TabLayout and ViewPager2 to display three fragments: Popular, New, and Favorites.
 */
public class MainActivity extends AppCompatActivity {

    // UI elements
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout for the main screen

        // Initialize UI components
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Set up the ViewPager with a custom adapter to manage fragments
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Attach TabLayout with ViewPager2 using TabLayoutMediator
        // Set tab titles dynamically based on position
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) tab.setText("Popular");
                    else if (position == 1) tab.setText("New");
                    else tab.setText("Favorites");
                }).attach();
    }
}
