package com.ghostreborn.akira.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.anilist.AnilistNetwork;
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

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        this,
                        R.array.anime_status,
                        android.R.layout.simple_spinner_item
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.animeStatusSpinner.setAdapter(adapter);

                binding.watchFab.setOnClickListener(v -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        Constants.allAnimeID = AllAnimeParser.allAnimeIdWithMalId(Constants.animeDetails.getAnimeName(), Constants.animeID);
                        AllAnimeParser.animeDetails(Constants.allAnimeID);
                        runOnUiThread(() -> {
                            startActivity(new Intent(this, EpisodesActivity.class));
                        });
                    });
                });

                binding.animeStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Handle item selection
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        Constants.animeStatus = selectedItem;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Handle case when nothing is selected
                    }
                });

                binding.animeProgressEditText.setText(Constants.animeProgress);
                binding.animeProgressAddButton.setOnClickListener(v -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        AnilistNetwork.saveAnimeProgress(Constants.animeID, binding.animeProgressEditText.getText().toString());
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                        });
                    });
                });

                binding.animeProgressDeleteButton.setOnClickListener(v -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        AnilistNetwork.deleteAnime(Constants.animeMediaListEntryID);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
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

                String animeProgress = "Watched " + Constants.animeProgress + " Episodes";
                binding.animeProgressTextView.setText(animeProgress);

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