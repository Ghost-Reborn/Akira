package com.ghostreborn.akira.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ghostreborn.akira.R;
import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.model.AnimeDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
        Button prequelButton = findViewById(R.id.prequel_button);
        Button sequelButton = findViewById(R.id.sequel_button);
        FloatingActionButton watchFAB = findViewById(R.id.watch_fab);

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            AnimeDetails details = AllAnimeParser.animeDetails(id);
            runOnUiThread(() -> {

                watchFAB.setOnClickListener(v -> {
                    Intent episodesIntent = new Intent(this, EpisodesActivity.class);
                    episodesIntent.putExtra("episodes", details.getEpisodes());
                    startActivity(episodesIntent);
                });

                Picasso.get().load(details.getAnimeBanner()).into(animeBannerImageView);
                Picasso.get().load(details.getAnimeImage()).into(animeThumbnailImageView);
                animeNameTextView.setText(details.getAnimeName());
                animeDescriptionTextView.setText(details.getAnimeDescription());

                if (!details.getAnimePrequel().isEmpty()){
                    prequelButton.setVisibility(View.VISIBLE);
                    prequelButton.setOnClickListener(v -> {
                        Intent animeIntent = new Intent(AnimeDetailsLayout.this, AnimeDetailsLayout.class);
                        animeIntent.putExtra("animeID", details.getAnimePrequel());
                        startActivity(animeIntent);
                        finish();
                    });
                }

                if (!details.getAnimeSequel().isEmpty()){
                    sequelButton.setVisibility(View.VISIBLE);
                    sequelButton.setOnClickListener(v -> {
                        Intent animeIntent = new Intent(AnimeDetailsLayout.this, AnimeDetailsLayout.class);
                        animeIntent.putExtra("animeID", details.getAnimeSequel());
                        startActivity(animeIntent);
                        finish();
                    });
                }
            });
        };
        executor.execute(task);

    }
}