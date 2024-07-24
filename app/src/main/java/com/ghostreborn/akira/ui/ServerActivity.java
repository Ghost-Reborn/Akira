package com.ghostreborn.akira.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.adapter.ServerAdapter;
import com.ghostreborn.akira.allAnime.AllAnimeParser;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        RecyclerView serverRecyclerView = findViewById(R.id.server_recycler_view);
        serverRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            ArrayList<String> servers = AllAnimeParser.getSourceUrls(Constants.animeID, Constants.animeEpisode);
            runOnUiThread(() -> serverRecyclerView.setAdapter(new ServerAdapter(this,servers)));
        };
        executor.execute(task);

    }
}