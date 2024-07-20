package com.ghostreborn.akira.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.R;
import com.ghostreborn.akira.adapter.EpisodeAdapter;
import com.ghostreborn.akira.adapter.EpisodeGroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class EpisodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        Intent intent = getIntent();
        ArrayList<String> episodes = intent.getStringArrayListExtra("episodes");
        RecyclerView episodesRecycler = findViewById(R.id.episodes_recycler_view);
        episodesRecycler.setLayoutManager(new LinearLayoutManager(this));
        assert episodes != null;
        List<String> groupedEpisodes = groupEpisodes(episodes).get(0);
        EpisodeAdapter adapter = new EpisodeAdapter(groupedEpisodes);
        episodesRecycler.setAdapter(adapter);

        RecyclerView episodeGroupRecycler = findViewById(R.id.episode_group_recycler_view);
        episodeGroupRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        EpisodeGroupAdapter episodeGroupAdapter = new EpisodeGroupAdapter(episodes, episodesRecycler);
        episodeGroupRecycler.setAdapter(episodeGroupAdapter);


    }

    public static List<List<String>> groupEpisodes(List<String> episodes) {
        List<List<String>> groupedEpisodes = new ArrayList<>();
        int startIndex = 0;
        while (startIndex < episodes.size()) {
            int endIndex = Math.min(startIndex + 100, episodes.size());
            groupedEpisodes.add(episodes.subList(startIndex, endIndex));
            startIndex = endIndex;
        }
        return groupedEpisodes;
    }
}