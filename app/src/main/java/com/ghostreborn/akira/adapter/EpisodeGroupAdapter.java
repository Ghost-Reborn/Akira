package com.ghostreborn.akira.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.allAnime.AllAnimeParser;

import java.util.concurrent.Executors;

public class EpisodeGroupAdapter extends RecyclerView.Adapter<EpisodeGroupAdapter.AnimeViewHolder> {

    private final RecyclerView recyclerView;
    private final AppCompatActivity activity;
    private final ProgressBar progressBar;

    public EpisodeGroupAdapter(AppCompatActivity activity, RecyclerView recyclerView, ProgressBar progressBar) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
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
            progressBar.setVisibility(View.VISIBLE);
            Executors.newSingleThreadExecutor().execute(() -> {
                AllAnimeParser.getEpisodeDetails(Constants.groupedEpisodes.get(position));
                activity.runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                    EpisodeAdapter adapter = new EpisodeAdapter(activity);
                    recyclerView.setAdapter(adapter);
                });
            });
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