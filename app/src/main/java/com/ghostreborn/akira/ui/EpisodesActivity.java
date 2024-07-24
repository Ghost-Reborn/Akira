package com.ghostreborn.akira.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.adapter.EpisodeAdapter;
import com.ghostreborn.akira.adapter.EpisodeGroupAdapter;
import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.model.Episode;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EpisodesActivity extends AppCompatActivity {

    public static ArrayList<ArrayList<String>> groupEpisodes(ArrayList<String> episodes) {
        ArrayList<ArrayList<String>> groupedEpisodes = new ArrayList<>();
        int startIndex = 0;
        while (startIndex < episodes.size()) {
            int endIndex = Math.min(startIndex + 8, episodes.size());
            groupedEpisodes.add(new ArrayList<>(episodes.subList(startIndex, endIndex)));
            startIndex = endIndex;
        }
        return groupedEpisodes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

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
        EpisodeGroupAdapter episodeGroupAdapter = new EpisodeGroupAdapter(episodesRecycler, this);
        episodeGroupRecycler.setAdapter(episodeGroupAdapter);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            ArrayList<Episode> updatedEpisodes = AllAnimeParser.getEpisodeDetails(Constants.animeID, episodes);
            runOnUiThread(() -> episodesRecycler.setAdapter(new EpisodeAdapter(updatedEpisodes)));
        };
        executor.execute(task);

    }
}