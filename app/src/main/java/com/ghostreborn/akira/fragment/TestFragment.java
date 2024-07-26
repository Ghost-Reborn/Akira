package com.ghostreborn.akira.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ghostreborn.akira.R;
import com.ghostreborn.akira.jikan.JikanParser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        TextView testText = view.findViewById(R.id.test_text);
        Executor executor = Executors.newSingleThreadExecutor();
        Runnable task = () -> {
            String out = JikanParser.getTopAnime();
            requireActivity().runOnUiThread(() -> testText.setText(out));
        };
        executor.execute(task);


        return view;
    }
}