package com.gi.ikhwanmusicandroid.actions;

/**
 * Created by gmochid on 2/4/16.
 */
public class PlayerAction {
    public static final String PLAYER_PLAY_SONG = "play_song";
    public static final String PLAYER_PLAY_CURRENT_SONG = "play_current_song";
    public static final String PLAYER_PLAY_RADIO = "play_radio";
    public static final String PLAYER_PAUSE = "pause";

    public static final String KEY_SONG = "key-song";

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
}
