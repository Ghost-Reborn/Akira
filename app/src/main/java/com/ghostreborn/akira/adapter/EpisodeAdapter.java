package com.ghostreborn.akira.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.fragment.ServerFragment;
import com.ghostreborn.akira.model.Episode;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.AnimeViewHolder> {

    private final AppCompatActivity activity;

    public EpisodeAdapter(AppCompatActivity activity) {
        this.activity = activity;
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
        Episode episode = Constants.parsedEpisodes.get(position);
        holder.episodeNumberTextView.setText(episode.getEpisodeNumber());
        holder.episodeTitleTextView.setText(episode.getEpisodeTitle());
        holder.itemView.setOnClickListener(v -> {
            ServerFragment fragment = new ServerFragment(episode.getEpisodeNumber());
            fragment.show(activity.getSupportFragmentManager(), "My Dialog");
        });
    }

    @Override
    public int getItemCount() {
        return Constants.parsedEpisodes.size();
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