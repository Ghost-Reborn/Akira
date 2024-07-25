package com.ghostreborn.akira.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;

import java.util.ArrayList;

public class EpisodeGroupAdapter extends RecyclerView.Adapter<EpisodeGroupAdapter.AnimeViewHolder> {

    private final RecyclerView recyclerView;

    public EpisodeGroupAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
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
            ArrayList<String> episodes = new ArrayList<>(Constants.groupedEpisodes.get(position));
            recyclerView.setAdapter(new EpisodeAdapter(episodes));
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