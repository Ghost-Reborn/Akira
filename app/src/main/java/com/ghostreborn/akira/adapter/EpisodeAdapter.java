package com.ghostreborn.akira.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.R;

import java.util.ArrayList;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.AnimeViewHolder> {

    private final ArrayList<String> episodes;
    public EpisodeAdapter(ArrayList<String> episodes){
        this.episodes = episodes;
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
        String episode = episodes.get(position);
        String episodeTitle = "Episode " + episode;
        holder.episodeNumberTextView.setText(episode);
        holder.episodeTitleTextView.setText(episodeTitle);
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        public TextView episodeNumberTextView;
        public TextView episodeTitleTextView;
        public ImageView animeEpisodeImageView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeNumberTextView = itemView.findViewById(R.id.episode_number_text_view);
            episodeTitleTextView = itemView.findViewById(R.id.episode_title_text_view);
            animeEpisodeImageView = itemView.findViewById(R.id.episode_thumbnail_image_view);
        }
    }
}