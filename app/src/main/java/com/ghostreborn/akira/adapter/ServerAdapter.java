package com.ghostreborn.akira.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.R;
import com.ghostreborn.akira.ui.PlayEpisodeActivity;

import java.util.List;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.AnimeViewHolder> {

    private final Context context;
    private final List<String> serverList;

    public ServerAdapter(Context context, List<String> serverList) {
        this.context = context;
        this.serverList = serverList;
    }

    @NonNull
    @Override
    public ServerAdapter.AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.server_list, parent, false);
        return new ServerAdapter.AnimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServerAdapter.AnimeViewHolder holder, int position) {
        holder.serverTextView.setText(serverList.get(position));
        holder.itemView.setOnClickListener(v -> {
            Constants.episodeUrl = serverList.get(position);
            context.startActivity(new Intent(context, PlayEpisodeActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return serverList.size();
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        public TextView serverTextView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            serverTextView = itemView.findViewById(R.id.server_text_view);
        }
    }
}