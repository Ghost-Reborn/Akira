package com.ghostreborn.akira.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.allAnime.AllAnimeParser;
import com.ghostreborn.akira.anilist.AnilistUtils;

import java.util.concurrent.Executors;

public class TestFragment extends Fragment {

    private static final String CLIENT_ID = "20149";
    private static final String REDIRECT_URI = "wanpisu://ghostreborn.in";
    SharedPreferences preferences;
    private TextView testText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preferences = getActivity().getSharedPreferences(Constants.sharedPreference, Context.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        testText = view.findViewById(R.id.test_text);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkLoggedIn();
        parseAccessToken();
        if (checkLoggedIn()){
            Executors.newSingleThreadExecutor().execute(() -> {
                String out = AllAnimeParser.allAnimeIdWithMalId("One Piece", "21");
                getActivity().runOnUiThread(() -> {
                    testText.setText(out);
                });
            });
        }
    }

    private boolean checkLoggedIn(){
        boolean isLoggedIn = preferences.getBoolean(Constants.akiraLoggedIn, false);
        if(!isLoggedIn){
            String queryUrl = "https://anilist.co/api/v2/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(queryUrl)));
            return false;
        }
        return true;
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