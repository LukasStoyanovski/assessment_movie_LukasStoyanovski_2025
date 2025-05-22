package com.example.movieexplorer.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.movieexplorer.fragments.PopularMoviesFragment;
import com.example.movieexplorer.fragments.NewMoviesFragment;
import com.example.movieexplorer.fragments.FavoritesFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return new PopularMoviesFragment();
        else if (position == 1) return new NewMoviesFragment();
        else return new FavoritesFragment();
    }

}
