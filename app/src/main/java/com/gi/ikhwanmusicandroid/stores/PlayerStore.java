package com.gi.ikhwanmusicandroid.stores;

import com.gi.ikhwanmusicandroid.actions.Action;
import com.gi.ikhwanmusicandroid.actions.Dispatcher;
import com.gi.ikhwanmusicandroid.actions.PlayerAction;
import com.gi.ikhwanmusicandroid.models.Song;
import com.gi.ikhwanmusicandroid.services.AudioService;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by gmochid on 2/4/16.
 */
public class PlayerStore extends Store {
    private static PlayerStore instance;
    private Song currentSong;
    private Boolean play;

    protected PlayerStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static PlayerStore get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new PlayerStore(dispatcher);
        }
        return instance;
    }

    @Override
    @Subscribe
    public void onAction(Action action) {
        switch (action.getType()) {
            case PlayerAction.PLAYER_PLAY:
                Song song = (Song) action.getData().get(PlayerAction.KEY_SONG);
                play(song);
                break;

            case PlayerAction.PLAYER_PAUSE:
                pause();
                break;

            default:
                return;
        }
        emitStoreChange();
    }

    private void play(Song song) {
        AudioService.getInstance().play(song.getUrl());
    }

    public void pause() {
        AudioService.getInstance().pause();
    }

    @Override
    StoreChangeEvent changeEvent() {
        return new PlayerStoreChangeEvent();
    }

    public class PlayerStoreChangeEvent implements StoreChangeEvent {
    }
}
