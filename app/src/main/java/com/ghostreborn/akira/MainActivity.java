package com.ghostreborn.akira;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.adapter.AnimeAdapter;
import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.model.Anime;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView animeRecyclerView = findViewById(R.id.anime_recycler_view);
        animeRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        SearchView animeSearchView = findViewById(R.id.anime_search_view);
        animeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Executor executor = Executors.newSingleThreadExecutor();
                Runnable task = () -> {
                    List<Anime> popularAnime = AllAnimeParser.searchAnime(animeSearchView.getQuery().toString());
                    runOnUiThread(() -> {
                        AnimeAdapter adapter = new AnimeAdapter(popularAnime);
                        animeRecyclerView.setAdapter(adapter);
                    });
                };
                executor.execute(task);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            List<Anime> popularAnime = AllAnimeParser.queryPopular();
            runOnUiThread(() -> {
                AnimeAdapter adapter = new AnimeAdapter(popularAnime);
                animeRecyclerView.setAdapter(adapter);
            });
        };
        executor.execute(task);

    }
}