package com.gi.ikhwanmusicandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gi.ikhwanmusicandroid.R;
import com.gi.ikhwanmusicandroid.models.Song;

import java.util.List;

/**
 * Created by gmochid on 1/31/16.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private List<Song> songs;

    public SongAdapter(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.songTitle.setText(song.getTitle());
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        protected TextView songTitle;

        public SongViewHolder(View view) {
            super(view);
            songTitle = (TextView) view.findViewById(R.id.home_card_title);
        }

    }
}
