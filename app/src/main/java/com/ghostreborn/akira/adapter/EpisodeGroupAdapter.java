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
import com.ghostreborn.akira.model.EpisodeDetails;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EpisodeGroupAdapter extends RecyclerView.Adapter<EpisodeGroupAdapter.AnimeViewHolder> {

    private final RecyclerView recyclerView;
    private final Activity activity;
    private final String animeID;

    public EpisodeGroupAdapter(RecyclerView recyclerView, Activity activity, String animeID) {
        this.recyclerView = recyclerView;
        this.activity = activity;
        this.animeID = animeID;
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
            recyclerView.setAdapter(new EpisodeAdapter(Constants.groupedEpisodes.get(position), new ArrayList<>(), new ArrayList<>()));
            Executor executor = Executors.newSingleThreadExecutor();
            Runnable task = () -> {
                ArrayList<String> parsedEpisodes = new ArrayList<>();
                ArrayList<String> episodeThumbnails = new ArrayList<>();
                for (int i=0;i<Constants.groupedEpisodes.get(position).size();i++) {
                    EpisodeDetails details = AllAnimeParser.episodeDetails(animeID, Constants.groupedEpisodes.get(position).get(i));
                    parsedEpisodes.add(details.getEpisodeTitle());
                    episodeThumbnails.add(details.getEpisodeThumbnail());
                }
                activity.runOnUiThread(() -> {
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                    recyclerView.setAdapter(new EpisodeAdapter(Constants.groupedEpisodes.get(position), parsedEpisodes, episodeThumbnails));
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
