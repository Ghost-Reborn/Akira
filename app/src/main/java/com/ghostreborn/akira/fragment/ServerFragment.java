package com.ghostreborn.akira.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.R;
import com.ghostreborn.akira.adapter.ServerAdapter;

import java.util.ArrayList;

public class ServerFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_server, container, false);
        RecyclerView fragmentRecyclerView = view.findViewById(R.id.server_recycler_view);
        fragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<String> servers = new ArrayList<>();
        servers.add("google.com");
        servers.add("google.com");
        servers.add("google.com");
        servers.add("google.com");
        servers.add("google.com");

        fragmentRecyclerView.setAdapter(new ServerAdapter(getContext(), servers));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width,height);
        }
    }
}