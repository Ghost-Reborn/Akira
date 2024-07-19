package com.ghostreborn.akira;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private List<Anime> animeList;
    public AnimeAdapter(List<Anime> animeList) {
        this.animeList = animeList;
    }

    @NonNull
    @Override
    public AnimeAdapter.AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anime_list_layout, parent, false);
        return new AnimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeAdapter.AnimeViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        holder.animeNameTextView.setText(anime.getAnimeName());
        Picasso.get().load(anime.getAnimeThumbnail()).into(holder.animeImageView);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        public TextView animeNameTextView;
        public ImageView animeImageView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            animeNameTextView = itemView.findViewById(R.id.animeNameTextView);
            animeImageView = itemView.findViewById(R.id.animeImageView);
        }
    }
}