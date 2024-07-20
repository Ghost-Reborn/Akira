package com.ghostreborn.akira.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ghostreborn.akira.R;
import com.ghostreborn.akira.allAnime.AllAnimeParser;

import java.util.ArrayList;
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
            ArrayList<String> sources = AllAnimeParser.getSourceUrls("ReooPAxPMsHM4KPMY", "1");
            StringBuilder out = new StringBuilder();
            for (int i=0;i<sources.size();i++){
                out.append(sources.get(i)).append("\n\n");
            }
            requireActivity().runOnUiThread(() -> testText.setText(out.toString()));
        };
        executor.execute(task);

        return view;
    }
}