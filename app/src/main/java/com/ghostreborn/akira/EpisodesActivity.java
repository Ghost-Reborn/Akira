package com.ghostreborn.akira;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.adapter.EpisodeAdapter;

import java.util.ArrayList;

public class EpisodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        Intent intent = getIntent();
        ArrayList<String> episodes = intent.getStringArrayListExtra("episodes");
        RecyclerView episodesRecycler = findViewById(R.id.episodes_recycler_view);
        episodesRecycler.setLayoutManager(new LinearLayoutManager(this));
        EpisodeAdapter adapter = new EpisodeAdapter(episodes);
        episodesRecycler.setAdapter(adapter);

    }
}