package com.ghostreborn.akira.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.AnilistConstants;
import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.adapter.AnimeAdapter;
import com.ghostreborn.akira.anilist.AnilistParser;

import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        preferences = getActivity().getSharedPreferences(Constants.sharedPreference, Context.MODE_PRIVATE);
        RecyclerView animeRecyclerView = view.findViewById(R.id.anime_recycler_view);
        SearchView animeSearchView = view.findViewById(R.id.anime_search_view);
        animeRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        setSearchView(animeSearchView, animeRecyclerView);
        queryPopular(animeRecyclerView);

        return view;
    }

    private void setSearchView(SearchView animeSearchView, RecyclerView animeRecyclerView) {
//        animeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Executors.newSingleThreadExecutor().execute(()->{
//                    AllAnimeParser.searchAnime(animeSearchView.getQuery().toString());
//                    requireActivity().runOnUiThread(() -> animeRecyclerView.setAdapter(new AnimeAdapter(getContext())));
//                });
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
    }

    private void queryPopular(RecyclerView animeRecyclerView) {
        Executors.newSingleThreadExecutor().execute(() -> {
            String token = preferences.getString(Constants.akiraToken, "");
            String userId = preferences.getString(Constants.akiraUserId, "");
            AnilistParser.animeList(token, userId, AnilistConstants.TYPE_ANIME, AnilistConstants.STATUS_CURRENT);
            requireActivity().runOnUiThread(() -> animeRecyclerView.setAdapter(new AnimeAdapter(getContext())));
        });
    }

}