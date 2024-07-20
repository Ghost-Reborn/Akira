package com.ghostreborn.akira.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.model.Episode;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EpisodeGroupAdapter extends RecyclerView.Adapter<EpisodeGroupAdapter.AnimeViewHolder> {

    private final RecyclerView recyclerView;
    private final Activity activity;

    public EpisodeGroupAdapter(RecyclerView recyclerView, Activity activity, String animeID) {
        this.recyclerView = recyclerView;
        this.activity = activity;
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
            recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            ArrayList<Episode> episodes = new ArrayList<>();
            for (int i=0;i<Constants.groupedEpisodes.get(position).size();i++) {
                episodes.add(new Episode(Constants.groupedEpisodes.get(position).get(i), "", ""));
            }
            recyclerView.setAdapter(new EpisodeAdapter(episodes));
            Executor executor = Executors.newSingleThreadExecutor();
            Runnable task = () -> {
                ArrayList<Episode> updatedEpisodes = AllAnimeParser.getEpisodeDetails(Constants.animeID, episodes);
                activity.runOnUiThread(() -> {
                    recyclerView.setAdapter(new EpisodeAdapter(updatedEpisodes));
                });
            };
            executor.execute(task);
        });
    }

    @Override
    public int getItemCount() {
        return Constants.groupedEpisodes.size();
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        TextView episodeGroupTextView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeGroupTextView = itemView.findViewById(R.id.episode_group_text_view);
        }
    }
}
