package com.ghostreborn.akira.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.adapter.EpisodeAdapter;
import com.ghostreborn.akira.adapter.EpisodeGroupAdapter;
import com.ghostreborn.akira.model.Episode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EpisodesActivity extends AppCompatActivity {

    public static List<List<String>> groupEpisodes(List<String> episodes) {
        List<List<String>> groupedEpisodes = new ArrayList<>();
        int startIndex = 0;
        while (startIndex < episodes.size()) {
            int endIndex = Math.min(startIndex + 8, episodes.size());
            groupedEpisodes.add(episodes.subList(startIndex, endIndex));
            startIndex = endIndex;
        }
        return groupedEpisodes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        Intent intent = getIntent();
        String animeID = intent.getStringExtra("animeID");
        Constants.episodes = intent.getStringArrayListExtra("episodes");
        RecyclerView episodesRecycler = findViewById(R.id.episodes_recycler_view);
        episodesRecycler.setLayoutManager(new LinearLayoutManager(this));
        Constants.groupedEpisodes = groupEpisodes(Constants.episodes);
        ArrayList<Episode> episodes = new ArrayList<>();
        for (int i=0;i<Constants.groupedEpisodes.get(0).size();i++) {
            episodes.add(new Episode(Constants.groupedEpisodes.get(0).get(i), "", ""));
        }
        EpisodeAdapter adapter = new EpisodeAdapter(episodes);
        episodesRecycler.setAdapter(adapter);

        RecyclerView episodeGroupRecycler = findViewById(R.id.episode_group_recycler_view);
        episodeGroupRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        EpisodeGroupAdapter episodeGroupAdapter = new EpisodeGroupAdapter(episodesRecycler, this, animeID);
        episodeGroupRecycler.setAdapter(episodeGroupAdapter);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            ArrayList<Episode> parsedEpisodes = new ArrayList<>();
            for (int i=0;i<Constants.groupedEpisodes.get(0).size();i++) {
                episodes.add(new Episode(Constants.groupedEpisodes.get(0).get(i), "", ""));
            }
            runOnUiThread(() -> {
                episodesRecycler.setLayoutManager(new LinearLayoutManager(this));
                episodesRecycler.setAdapter(new EpisodeAdapter(parsedEpisodes));
            });
        };
        executor.execute(task);

    }
}