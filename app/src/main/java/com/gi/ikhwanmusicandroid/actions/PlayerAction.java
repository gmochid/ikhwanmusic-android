package com.gi.ikhwanmusicandroid.actions;

/**
 * Created by gmochid on 2/4/16.
 */
public class PlayerAction {
    public static final String PLAYER_PLAY = "play";
    public static final String PLAYER_PAUSE = "pause";

    public static final String KEY_SONG = "key-song";

    private static PlayerAction instance;
    final Dispatcher dispatcher;

    PlayerAction(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public static PlayerAction get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new PlayerAction(dispatcher);
        }
        return instance;
    }
}
