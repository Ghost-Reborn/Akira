package com.ghostreborn.akira.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ghostreborn.akira.R;

public class TestFragment extends Fragment {

    private static final String CLIENT_ID = "20149";
    private static final String CLIENT_SECRET = "A7EbTSCsqlaG0s3drrWspURPmmSbLvOMiX5epzZG";
    private static final String REDIRECT_URI = "wanpisu://ghostreborn.in";
    private static final String AUTH_URL = "https://anilist.co/api/v2/oauth/authorize?client_id=" + CLIENT_ID + "&response_type=code&redirect_uri=" + REDIRECT_URI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        String queryUrl = "https://anilist.co/api/v2/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";

        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(queryUrl)));

        return view;
    }
}