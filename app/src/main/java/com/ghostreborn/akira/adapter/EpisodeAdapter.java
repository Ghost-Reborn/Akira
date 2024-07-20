package com.ghostreborn.akira.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.R;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.AnimeViewHolder> {

    private final List<String> episodeList;
    private final List<String> parsedEpisodeList;

    public EpisodeAdapter(List<String> episodeList, List<String> parsedEpisodeList) {
        this.episodeList = episodeList;
        this.parsedEpisodeList = parsedEpisodeList;
    }

    @NonNull
    @Override
    public EpisodeAdapter.AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episodes_list, parent, false);
        return new AnimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.AnimeViewHolder holder, int position) {
        holder.episodeNumberTextView.setText(episodeList.get(position));
        if (!parsedEpisodeList.isEmpty()) {
            holder.episodeTitleTextView.setText(parsedEpisodeList.get(position));
        } else {
            String episodeTitle = "Episode " + episodeList.get(position);
            holder.episodeTitleTextView.setText(episodeTitle);
        }
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        public TextView episodeNumberTextView;
        public TextView episodeTitleTextView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeNumberTextView = itemView.findViewById(R.id.episode_number_text_view);
            episodeTitleTextView = itemView.findViewById(R.id.episode_title_text_view);
        }
    }
}