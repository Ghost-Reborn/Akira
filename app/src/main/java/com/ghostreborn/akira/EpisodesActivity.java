package com.ghostreborn.akira;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EpisodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        Intent intent = getIntent();
        ArrayList<String> episodes = intent.getStringArrayListExtra("episodes");

    }
}