package com.ghostreborn.akira;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ghostreborn.akira.fragment.HomeFragment;
import com.ghostreborn.akira.fragment.MangaFragment;
import com.ghostreborn.akira.fragment.TestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // TODO work on Manga
    // TODO fix episodes with null as episode title
    // TODO fix episodes with null as episodeInfo in parsing section
    // TODO show anime thumbnail if episode thumbnail is not available
    // TODO show "Episode 101" like title above server returned title
    //  with different color and font
    // TODO show whether episode is filler or not when possible

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment_container, new TestFragment())
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