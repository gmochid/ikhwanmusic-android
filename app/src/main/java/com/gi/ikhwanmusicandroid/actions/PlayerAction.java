package com.gi.ikhwanmusicandroid.actions;

import com.gi.ikhwanmusicandroid.models.Song;

import java.util.ArrayList;

/**
 * Created by gmochid on 2/4/16.
 */
public class PlayerAction {
    public static final String PLAYER_PLAY_SONG = "play_song";
    public static final String PLAYER_PLAY_CURRENT_SONG = "play_current_song";
    public static final String PLAYER_PLAY_RADIO = "play_radio";
    public static final String PLAYER_PAUSE = "pause";
    public static final String PLAYER_NEXT = "next";
    public static final String PLAYER_PREVIOUS = "previous";
    public static final String PLAYER_RANDOM_PLAYLIST = "random_playlist";

    public static final String KEY_SONG = "key_song";

    private static PlayerAction instance;
    final Dispatcher dispatcher;

    PlayerAction(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public static PlayerAction getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new PlayerAction(dispatcher);
        }
        return instance;
    }

    public void playSong(Song song) {
        dispatcher.dispatch(PLAYER_PLAY_SONG,
                KEY_SONG, song);
    }

    public void playCurrentSong() {
        dispatcher.dispatch(PLAYER_PLAY_CURRENT_SONG);
    }

    public void playRadio() {
        dispatcher.dispatch(PLAYER_PLAY_RADIO);
    }

    public void pause() {
        dispatcher.dispatch(PLAYER_PAUSE);
    }

    public void generateRandomPlaylist() {
        dispatcher.dispatch(PLAYER_RANDOM_PLAYLIST);
    }

    public void next() {
        dispatcher.dispatch(PLAYER_NEXT);
    }

    public void previous() {
        dispatcher.dispatch(PLAYER_PREVIOUS);
    }
}
