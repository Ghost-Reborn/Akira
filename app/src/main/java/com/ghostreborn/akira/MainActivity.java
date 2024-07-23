package com.ghostreborn.akira;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ghostreborn.akira.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment_container, new HomeFragment())
                .commit();

    }
}