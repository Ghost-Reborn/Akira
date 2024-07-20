package com.ghostreborn.akira.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.R;
import com.ghostreborn.akira.adapter.EpisodeAdapter;
import com.ghostreborn.akira.adapter.EpisodeGroupAdapter;
import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.model.EpisodeDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EpisodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        Intent intent = getIntent();
        String animeID = intent.getStringExtra("animeID");
        ArrayList<String> episodes = intent.getStringArrayListExtra("episodes");
        RecyclerView episodesRecycler = findViewById(R.id.episodes_recycler_view);
        episodesRecycler.setLayoutManager(new LinearLayoutManager(this));
        assert episodes != null;
        List<String> groupedEpisodes = groupEpisodes(episodes).get(0);
        EpisodeAdapter adapter = new EpisodeAdapter(groupedEpisodes, new ArrayList<>());
        episodesRecycler.setAdapter(adapter);

        RecyclerView episodeGroupRecycler = findViewById(R.id.episode_group_recycler_view);
        episodeGroupRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        EpisodeGroupAdapter episodeGroupAdapter = new EpisodeGroupAdapter(episodes, episodesRecycler, this,animeID);
        episodeGroupRecycler.setAdapter(episodeGroupAdapter);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            List<String> parsedEpisodes = new ArrayList<>();
            for (int i=0;i<groupedEpisodes.size();i++){
                EpisodeDetails episodeDetails = AllAnimeParser.episodeDetails(animeID, groupedEpisodes.get(i));
                parsedEpisodes.add(episodeDetails.getEpisodeTitle());
            }
            runOnUiThread(() -> {
                EpisodeAdapter episodeAdapter = new EpisodeAdapter(groupedEpisodes,parsedEpisodes);
                episodesRecycler.setAdapter(episodeAdapter);
            });
        };
        executor.execute(task);

    }

    public static List<List<String>> groupEpisodes(List<String> episodes) {
        List<List<String>> groupedEpisodes = new ArrayList<>();
        int startIndex = 0;
        while (startIndex < episodes.size()) {
            int endIndex = Math.min(startIndex + 15, episodes.size());
            groupedEpisodes.add(episodes.subList(startIndex, endIndex));
            startIndex = endIndex;
        }
        return groupedEpisodes;
    }
}