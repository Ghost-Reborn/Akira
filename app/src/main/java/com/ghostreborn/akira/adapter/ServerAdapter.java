package com.ghostreborn.akira.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ghostreborn.akira.Constants;
import com.ghostreborn.akira.PlayEpisodeActivity;
import com.ghostreborn.akira.R;

import java.util.List;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.AnimeViewHolder> {

    private final List<String> serverList;

    public ServerAdapter(List<String> serverList) {
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
            Intent intent = new Intent(holder.itemView.getContext(), PlayEpisodeActivity.class);
            Constants.episodeUrl = serverList.get(position);
            holder.itemView.getContext().startActivity(intent);
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