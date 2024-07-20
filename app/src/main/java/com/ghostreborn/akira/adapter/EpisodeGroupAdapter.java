package com.ghostreborn.akira.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.R;
import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.model.EpisodeDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EpisodeGroupAdapter extends RecyclerView.Adapter<EpisodeGroupAdapter.AnimeViewHolder> {

    private final List<List<String>> episodeGroups;
    private final RecyclerView recyclerView;
    private final Activity activity;
    private final String animeID;

    public EpisodeGroupAdapter(List<String> ungroupedEpisodes, RecyclerView recyclerView, Activity activity, String animeID) {
        episodeGroups = groupEpisodes(ungroupedEpisodes);
        this.recyclerView = recyclerView;
        this.activity = activity;
        this.animeID = animeID;
    }

    private static List<List<String>> groupEpisodes(List<String> episodes) {
        List<List<String>> groupedEpisodes = new ArrayList<>();
        int startIndex = 0;
        while (startIndex < episodes.size()) {
            int endIndex = Math.min(startIndex + 15, episodes.size());
            groupedEpisodes.add(episodes.subList(startIndex, endIndex));
            startIndex = endIndex;
        }
        return groupedEpisodes;
    }

    @NonNull
    @Override
    public EpisodeGroupAdapter.AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_group_list, parent, false);
        return new AnimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeGroupAdapter.AnimeViewHolder holder, int position) {
        String page = position + 1 + "";
        holder.episodeGroupTextView.setText(page);
        holder.episodeGroupTextView.setOnClickListener(v -> {
            Executor executor = Executors.newSingleThreadExecutor();
            Runnable task = () -> {
                List<String> parsedEpisodes = new ArrayList<>();
                for (int i=0;i<episodeGroups.get(position).size();i++){
                    EpisodeDetails episodeDetails = AllAnimeParser.episodeDetails(animeID, episodeGroups.get(position).get(i));
                    parsedEpisodes.add(episodeDetails.getEpisodeTitle());
                }
                activity.runOnUiThread(() -> {
                    recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
                    recyclerView.setAdapter(new EpisodeAdapter(episodeGroups.get(position), parsedEpisodes));
                });
            };
            executor.execute(task);
        });
    }

    @Override
    public int getItemCount() {
        return episodeGroups.size();
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        TextView episodeGroupTextView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeGroupTextView = itemView.findViewById(R.id.episode_group_text_view);
        }
    }
}
