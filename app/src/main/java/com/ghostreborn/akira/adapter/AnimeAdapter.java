package com.ghostreborn.akira.adapter;

import android.content.Context;
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
import com.ghostreborn.akira.model.Anime;
import com.ghostreborn.akira.ui.AnimeDetailsActivity;
import com.squareup.picasso.Picasso;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private final Context context;

    public AnimeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_list_layout, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        Anime anime = Constants.animes.get(position);
        holder.animeNameTextView.setText(anime.getAnimeName());
        Picasso.get().load(anime.getAnimeThumbnail()).into(holder.animeImageView);
        holder.itemView.setOnClickListener(v -> {
            Constants.animeID = anime.getAnimeID();
            Constants.animeProgress = anime.getProgress();
            Constants.animeMediaListEntryID = anime.getMediaListEntryId();
            context.startActivity(new Intent(context, AnimeDetailsActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return Constants.animes.size();
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        final TextView animeNameTextView;
        final ImageView animeImageView;

        AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            animeNameTextView = itemView.findViewById(R.id.anime_name_text_view);
            animeImageView = itemView.findViewById(R.id.animeImageView);
        }
    }
}