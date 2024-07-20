package com.ghostreborn.akira.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.adapter.AnimeAdapter;
import com.ghostreborn.akira.allAnime.AllAnimeParser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView animeRecyclerView = view.findViewById(R.id.anime_recycler_view);
        animeRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));

        SearchView animeSearchView = view.findViewById(R.id.anime_search_view);
        animeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Executor executor = Executors.newSingleThreadExecutor();
                Runnable task = () -> {
                    Constants.animes = AllAnimeParser.searchAnime(animeSearchView.getQuery().toString());
                    requireActivity().runOnUiThread(() -> {
                        AnimeAdapter adapter = new AnimeAdapter(getContext(), Constants.animes);
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
            Constants.animes = AllAnimeParser.queryPopular();
            requireActivity().runOnUiThread(() -> {
                AnimeAdapter adapter = new AnimeAdapter(getContext(), Constants.animes);
                animeRecyclerView.setAdapter(adapter);
            });
        };
        executor.execute(task);

        return view;
    }
}