package com.ghostreborn.akira;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView testText = findViewById(R.id.test_text);
        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            String rawJSON = AllAnimeNetwork.connectAllAnime("http://example.com");
            runOnUiThread(() -> testText.setText(rawJSON));
        };
        executor.execute(task);

    }
}