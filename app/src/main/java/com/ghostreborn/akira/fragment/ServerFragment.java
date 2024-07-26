package com.ghostreborn.akira.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.adapter.ServerAdapter;
import com.ghostreborn.akira.allAnime.AllAnimeParser;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ServerFragment extends DialogFragment {

    private final String episodeNumber;
    public ServerFragment(String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_server, container, false);
        RecyclerView fragmentRecyclerView = view.findViewById(R.id.server_recycler_view);
        ProgressBar serverProgressBar = view.findViewById(R.id.server_progress_bar);
        fragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Executors.newSingleThreadExecutor().execute(() -> {
            ArrayList<String> servers = AllAnimeParser.getSourceUrls(Constants.animeID, episodeNumber);
            getActivity().runOnUiThread(() -> {
                serverProgressBar.setVisibility(View.GONE);
                fragmentRecyclerView.setAdapter(new ServerAdapter(getContext(), servers));
            });
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width,height);
        }
    }
}