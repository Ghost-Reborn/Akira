package com.ghostreborn.akira.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.databinding.ActivityAnimeDetailsBinding;
import com.ghostreborn.akira.model.AnimeDetails;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AnimeDetailsActivity extends AppCompatActivity {

    private ActivityAnimeDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnimeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            AnimeDetails details = AllAnimeParser.animeDetails(Constants.animeID);
            runOnUiThread(() -> {

                binding.watchFab.setOnClickListener(v -> startActivity(new Intent(this, EpisodesActivity.class)));

                Picasso.get().load(details.getAnimeBanner()).into(binding.animeBannerImageView);
                Picasso.get().load(details.getAnimeImage()).into(binding.animeThumbnailImageView);
                binding.animeNameTextView.setText(details.getAnimeName());
                binding.animeDescriptionTextView.setText(details.getAnimeDescription());

                if (!details.getAnimePrequel().isEmpty()){
                    binding.prequelButton.setVisibility(View.VISIBLE);
                    binding.prequelButton.setOnClickListener(v -> {
                        Constants.animeID = details.getAnimePrequel();
                        startActivity(new Intent(AnimeDetailsActivity.this, AnimeDetailsActivity.class));
                        finish();
                    });
                }

                if (!details.getAnimeSequel().isEmpty()){
                    binding.sequelButton.setVisibility(View.VISIBLE);
                    binding.sequelButton.setOnClickListener(v -> {
                        Constants.animeID = details.getAnimeSequel();
                        startActivity(new Intent(AnimeDetailsActivity.this, AnimeDetailsActivity.class));
                        finish();
                    });
                }
            });
        };
        executor.execute(task);

    }
}