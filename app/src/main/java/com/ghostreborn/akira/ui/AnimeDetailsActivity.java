package com.ghostreborn.akira.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.databinding.ActivityAnimeDetailsBinding;
import com.ghostreborn.akira.model.AnimeDetails;
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
            AnimeDetails details = AllAnimeParser.animeDetails(Constants.animeID);
            runOnUiThread(() -> {

                if (details == null) {
                    finish();
                    return;
                }

                binding.watchFab.setOnClickListener(v -> startActivity(new Intent(this, EpisodesActivity.class)));

                Glide.with(this)
                        .load(details.getAnimeBanner())
                        .transform(new BlurTransformation(10,3))
                        .into(binding.animeBanner);
                Picasso.get().load(details.getAnimeImage()).into(binding.animeThumbnail);
                binding.animeName.setText(details.getAnimeName());
                binding.animeDescription.setText(details.getAnimeDescription());

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