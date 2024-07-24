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
import com.ghostreborn.akira.allManga.AllMangaParser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MangaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga, container, false);

        RecyclerView mangaRecyclerView = view.findViewById(R.id.manga_recycler_view);
        mangaRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));

        SearchView mangaSearchView = view.findViewById(R.id.manga_search_view);
        mangaSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Executor executor = Executors.newSingleThreadExecutor();
                Runnable task = () -> {
                    Constants.animes = AllMangaParser.searchManga(mangaSearchView.getQuery().toString());
                    requireActivity().runOnUiThread(() -> {
                        AnimeAdapter adapter = new AnimeAdapter(getContext());
                        mangaRecyclerView.setAdapter(adapter);
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
            Constants.animes = AllMangaParser.queryPopular();
            requireActivity().runOnUiThread(() -> {
                AnimeAdapter adapter = new AnimeAdapter(getContext());
                mangaRecyclerView.setAdapter(adapter);
            });
        };
        executor.execute(task);

        return view;
    }
}