package com.gi.ikhwanmusicandroid.store;

import com.gi.ikhwanmusicandroid.models.Song;
import com.gi.ikhwanmusicandroid.services.AudioService;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gmochid on 2016/02/03.
 */
public class SongStore implements Serializable {

    private Song currentSong;
    private ArrayList<CurrentSongListener> currentSongListeners;

    public SongStore() {
        currentSong = new Song("Mengenal Nabi", "https://s3-ap-southeast-1.amazonaws.com/ikhwan-music/Mengenal+Nabi.mp3", "Qatrunnada");
        currentSongListeners = new ArrayList<>();
    }

    public void play() {
        AudioService.getInstance().play(getCurrentSong());
    }

    public void pause() {
        AudioService.getInstance().pause();
    }

    public void subscribe(CurrentSongListener listener) {
        currentSongListeners.add(listener);
    }

    public void notifyChange() {
        for (CurrentSongListener listener : currentSongListeners)
            listener.onCurrentSongChanged(getCurrentSong());
    }

    public interface CurrentSongListener {
        void onCurrentSongChanged(Song song);
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;

        notifyChange();
    }
}
