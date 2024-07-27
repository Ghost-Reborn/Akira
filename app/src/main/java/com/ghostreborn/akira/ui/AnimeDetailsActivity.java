package com.ghostreborn.akira.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.databinding.ActivityAnimeDetailsBinding;
import com.ghostreborn.akira.jikan.JikanParser;
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
            JikanParser.animeDetails(Constants.animeID);
            runOnUiThread(() -> {

                if (Constants.animeDetails == null) {
                    finish();
                    return;
                }

                binding.watchFab.setOnClickListener(v -> startActivity(new Intent(this, EpisodesActivity.class)));

                Picasso.get().load(Constants.animeDetails.getAnimeImage()).into(binding.animeThumbnail);
                binding.animeName.setText(Constants.animeDetails.getAnimeName());
                binding.animeDescription.setText(Constants.animeDetails.getAnimeDescription());

            });
        };
        executor.execute(task);

    }
}