package com.ghostreborn.akira.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.anilist.AnilistNetwork;

import java.util.concurrent.Executors;

public class PlayEpisodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_episode);

        VideoView videoView = findViewById(R.id.anime_video_view);
        videoView.setVideoURI(Uri.parse(Constants.episodeUrl));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();

        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int currentPosition =videoView.getCurrentPosition();
                int duration = videoView.getDuration();
                if (duration > 0 && (float) currentPosition / duration >= 0.75) {
                    saveProgress(Constants.currentEpisode);
                    handler.removeCallbacks(this);
                } else {
                    handler.postDelayed(this, 100);
                }
            }
        };

        videoView.setOnPreparedListener(mediaPlayer -> handler.post(runnable));

    }

    private void saveProgress(String progress){
        Executors.newSingleThreadExecutor().execute(() -> {
            AnilistNetwork.saveAnimeProgress(Constants.animeID, progress);
            runOnUiThread(() -> {
                Toast.makeText(this,"Saved!", Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}