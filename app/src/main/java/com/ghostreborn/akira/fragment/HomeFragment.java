package com.ghostreborn.akira.fragment;

import static com.ghostreborn.akira.AnilistConstants.CLIENT_ID;
import static com.ghostreborn.akira.AnilistConstants.REDIRECT_URI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.ghostreborn.akira.anilist.AnilistUtils;

import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preferences = getActivity().getSharedPreferences(Constants.sharedPreference, Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView animeRecyclerView = view.findViewById(R.id.anime_recycler_view);
        SearchView animeSearchView = view.findViewById(R.id.anime_search_view);
        animeRecyclerView.setLayoutManager(new GridLayoutManager(requireActivity(), 3));
        setSearchView(animeSearchView, animeRecyclerView);
        getUserAnime(animeRecyclerView, AnilistConstants.STATUS_CURRENT);

        view.findViewById(R.id.status_current_button).setOnClickListener(v -> getUserAnime(animeRecyclerView, AnilistConstants.STATUS_CURRENT));
        view.findViewById(R.id.status_planning_button).setOnClickListener(v -> getUserAnime(animeRecyclerView, AnilistConstants.STATUS_PLANNING));
        view.findViewById(R.id.status_completed_button).setOnClickListener(v -> getUserAnime(animeRecyclerView, AnilistConstants.STATUS_COMPLETED));
        view.findViewById(R.id.status_dropped_button).setOnClickListener(v -> getUserAnime(animeRecyclerView, AnilistConstants.STATUS_DROPPED));
        view.findViewById(R.id.status_paused_button).setOnClickListener(v -> getUserAnime(animeRecyclerView, AnilistConstants.STATUS_PAUSED));
        view.findViewById(R.id.status_repeating_button).setOnClickListener(v -> getUserAnime(animeRecyclerView, AnilistConstants.STATUS_REPEATING));


        return view;
    }

    private void setSearchView(SearchView animeSearchView, RecyclerView animeRecyclerView) {
        animeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Executors.newSingleThreadExecutor().execute(()->{
                    AnilistParser.searchAnime(animeSearchView.getQuery().toString());
                    requireActivity().runOnUiThread(() -> animeRecyclerView.setAdapter(new AnimeAdapter(getContext())));
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getUserAnime(RecyclerView animeRecyclerView, String status) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AnilistParser.animeList(AnilistConstants.TYPE_ANIME, status);
            requireActivity().runOnUiThread(() -> animeRecyclerView.setAdapter(new AnimeAdapter(getContext())));
        });
    }

    @Override
    public void onResume() {
        super.onResume();checkLoggedIn();
        parseAccessToken();
        checkLoggedIn();
    }

    private void checkLoggedIn(){
        boolean isLoggedIn = preferences.getBoolean(Constants.akiraLoggedIn, false);
        if(!isLoggedIn){
            String queryUrl = "https://anilist.co/api/v2/oauth/authorize?client_id="+ CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(queryUrl)));
        }
    }

    private void parseAccessToken(){
        Uri uri = getActivity().getIntent().getData();
        if (uri != null) {
            String code = uri.getQueryParameter("code");
            Executors.newSingleThreadExecutor().execute(() -> {
                AnilistUtils.getToken(code, getActivity());
            });
        }
    }
}