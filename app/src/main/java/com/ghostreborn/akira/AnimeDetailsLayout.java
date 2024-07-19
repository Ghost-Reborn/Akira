package com.ghostreborn.akira;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.model.AnimeDetails;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AnimeDetailsLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details_layout);

        Intent intent = getIntent();
        String id = intent.getStringExtra("animeID");

        ImageView animeBannerImageView = findViewById(R.id.anime_banner_image_view);
        ImageView animeThumbnailImageView = findViewById(R.id.anime_thumbnail_image_view);
        TextView animeNameTextView = findViewById(R.id.anime_name_text_view);
        TextView animeDescriptionTextView = findViewById(R.id.anime_description_text_view);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            AnimeDetails details = AllAnimeParser.animeDetails(id);
            runOnUiThread(() -> {
                Picasso.get().load(details.getAnimeBanner()).into(animeBannerImageView);
                Picasso.get().load(details.getAnimeImage()).into(animeThumbnailImageView);
                animeNameTextView.setText(details.getAnimeName());
                animeDescriptionTextView.setText(details.getAnimeDescription());
            });
        };
        executor.execute(task);

    }
}