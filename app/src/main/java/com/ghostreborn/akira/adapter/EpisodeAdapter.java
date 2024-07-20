package com.ghostreborn.akira.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.AnimeViewHolder> {

    private final List<String> episodeList;
    private final List<String> parsedEpisodeList;
    private final List<String> episodeThumbnailList;

    public EpisodeAdapter(List<String> episodeList, List<String> parsedEpisodeList, List<String> episodeThumbnailList) {
        this.episodeList = episodeList;
        this.parsedEpisodeList = parsedEpisodeList;
        this.episodeThumbnailList = episodeThumbnailList;
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
            Picasso.get().load(episodeThumbnailList.get(position))
                    .into(holder.animeEpisodeImageView);
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
        public ImageView animeEpisodeImageView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            episodeNumberTextView = itemView.findViewById(R.id.episode_number_text_view);
            episodeTitleTextView = itemView.findViewById(R.id.episode_title_text_view);
            animeEpisodeImageView = itemView.findViewById(R.id.episode_thumbnail_image_view);
        }
    }
}