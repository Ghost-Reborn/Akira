package com.ghostreborn.akira.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.anilist.AnilistParser;

public class TestFragment extends Fragment {

    private static final String CLIENT_ID = "20149";
    private static final String REDIRECT_URI = "wanpisu://ghostreborn.in";
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences = getActivity().getSharedPreferences(Constants.sharedPreference, Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean(Constants.akiraLoggedIn, false);
        if(!isLoggedIn){
            String queryUrl = "https://anilist.co/api/v2/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(queryUrl)));
        }else{
            Log.e("TAG", preferences.getString(Constants.akiraToken, "NO VALUE"));
        }
        Uri uri = getActivity().getIntent().getData();
        if (uri != null) {
            String code = uri.getQueryParameter("code");
            AnilistParser.getToken(code, getActivity());
        }
    }
}