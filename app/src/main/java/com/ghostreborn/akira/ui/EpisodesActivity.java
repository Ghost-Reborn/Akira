package com.ghostreborn.akira.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.adapter.EpisodeAdapter;
import com.ghostreborn.akira.adapter.EpisodeGroupAdapter;
import com.ghostreborn.akira.jikan.JikanParser;

import java.util.concurrent.Executors;

public class EpisodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        RecyclerView episodesRecycler = findViewById(R.id.episodes_recycler_view);
        RecyclerView episodeGroupRecycler = findViewById(R.id.episode_group_recycler_view);
        ProgressBar episodeProgress = findViewById(R.id.episode_progress_bar);

        Executors.newSingleThreadExecutor().execute(() -> {
            JikanParser.episodeDetails(Constants.animeID, "1");
            runOnUiThread(() -> {
                episodeProgress.setVisibility(View.GONE);
                episodesRecycler.setLayoutManager(new LinearLayoutManager(this));
                EpisodeAdapter adapter = new EpisodeAdapter(this);
                episodesRecycler.setAdapter(adapter);
                episodeGroupRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                EpisodeGroupAdapter episodeGroupAdapter = new EpisodeGroupAdapter(this, episodesRecycler, episodeProgress);
                episodeGroupRecycler.setAdapter(episodeGroupAdapter);
            });
        });

    }
}