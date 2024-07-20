package com.ghostreborn.akira.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.ServerActivity;
import com.ghostreborn.akira.model.Episode;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.AnimeViewHolder> {

    private final ArrayList<Episode> episodes;

    public EpisodeAdapter(ArrayList<Episode> episodes) {
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
        holder.episodeNumberTextView.setText(episodes.get(position).getEpisodeNumber());
        if (!episodes.get(position).getEpisodeTitle().isEmpty()) {
            holder.episodeTitleTextView.setText(episodes.get(position).getEpisodeTitle());
            Picasso.get().load(episodes.get(position).getEpisodeThumbnail())
                    .into(holder.animeEpisodeImageView);
        } else {
            String episodeTitle = "Episode " + episodes.get(position).getEpisodeNumber();
            holder.episodeTitleTextView.setText(episodeTitle);
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ServerActivity.class);
            Constants.animeEpisode = episodes.get(position).getEpisodeNumber();
            holder.itemView.getContext().startActivity(intent);
        });
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