package com.ghostreborn.akira.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ghostreborn.akira.R;

import java.util.concurrent.Executors;

public class TestFragment extends Fragment {

    private TextView testText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        testText = view.findViewById(R.id.test_text);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Executors.newSingleThreadExecutor().execute(() -> {
            getActivity().runOnUiThread(() -> {
                testText.setText("out");
            });
        });

    }
}