package com.ghostreborn.akira.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.anilist.AnilistParser;
import com.ghostreborn.akira.databinding.ActivityAnimeDetailsBinding;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class AnimeDetailsActivity extends AppCompatActivity {

    private ActivityAnimeDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnimeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            AnilistParser.animeDetails(Constants.animeID);
            runOnUiThread(() -> {

                if (Constants.animeDetails == null) {
                    finish();
                    return;
                }

                binding.watchFab.setOnClickListener(v -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        Constants.allAnimeID = AllAnimeParser.allAnimeIdWithMalId("One Piece", "21");
                        AllAnimeParser.animeDetails(Constants.allAnimeID);
                        runOnUiThread(() -> {
                            startActivity(new Intent(this, EpisodesActivity.class));
                        });
                    });
                });

                Glide.with(this)
                        .load(Constants.animeDetails.getAnimeBanner())
                        .transform(new BlurTransformation(4,3))
                        .into(binding.animeBanner);
                Picasso.get().load(Constants.animeDetails.getAnimeImage()).into(binding.animeThumbnail);
                binding.animeName.setText(Constants.animeDetails.getAnimeName());
                binding.animeDescription.setText(Constants.animeDetails.getAnimeDescription());

                if (!Constants.animeDetails.getAnimePrequel().isEmpty()){
                    binding.prequelButton.setVisibility(View.VISIBLE);
                    binding.prequelButton.setOnClickListener(v -> {
                        Constants.animeID = Constants.animeDetails.getAnimePrequel();
                        startActivity(new Intent(AnimeDetailsActivity.this, AnimeDetailsActivity.class));
                        finish();
                    });
                }

                if (!Constants.animeDetails.getAnimeSequel().isEmpty()){
                    binding.sequelButton.setVisibility(View.VISIBLE);
                    binding.sequelButton.setOnClickListener(v -> {
                        Constants.animeID = Constants.animeDetails.getAnimeSequel();
                        startActivity(new Intent(AnimeDetailsActivity.this, AnimeDetailsActivity.class));
                        finish();
                    });
                }
            });
        };
        executor.execute(task);

    }
}