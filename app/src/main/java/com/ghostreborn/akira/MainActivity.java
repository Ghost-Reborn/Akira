package com.ghostreborn.akira;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ghostreborn.akira.fragment.HomeFragment;
import com.ghostreborn.akira.fragment.MangaFragment;
import com.ghostreborn.akira.fragment.TestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // TODO either show episode list without titles from server
    //  or show everything at a time with a progress bar
    // TODO show a bottom progress bar that updates with the progress of
    //  episode getting
    // TODO rework episode selection screen
    // TODO show server selection as a popup
    // TODO work on Manga

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment_container, new HomeFragment())
                .commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;

            int id = item.getItemId();
            if (id == R.id.nav_anime) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_manga) {
                selectedFragment = new MangaFragment();
            }else {
                selectedFragment = new TestFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, selectedFragment).commit();
            return true;
        });

    }
}