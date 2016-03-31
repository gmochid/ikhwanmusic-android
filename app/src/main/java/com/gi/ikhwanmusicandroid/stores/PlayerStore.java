package com.gi.ikhwanmusicandroid.stores;

import com.gi.ikhwanmusicandroid.actions.Action;
import com.gi.ikhwanmusicandroid.actions.Dispatcher;
import com.gi.ikhwanmusicandroid.actions.PlayerAction;
import com.gi.ikhwanmusicandroid.models.Song;
import com.gi.ikhwanmusicandroid.services.AudioService;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by gmochid on 2/4/16.
 */
public class PlayerStore extends Store implements AudioService.AudioServiceListener {
    public enum PlayingMode {
        SONG,
        RADIO
    }

    public enum PlayingStatus {
        PLAY,
        PAUSE,
        NOT_STARTED
    }

    private static PlayerStore instance;
    private final Song radioSong = new Song("Ikhwan Radio", "http://philae.shoutca.st:8343/stream", "");
    private Song currentSong;
    private ArrayList<Song> playlist;
    private PlayingStatus playingStatus;
    private PlayingMode playingMode;

    protected PlayerStore(Dispatcher dispatcher) {
        super(dispatcher);
        playingStatus = PlayingStatus.NOT_STARTED;
        currentSong = new Song("Tes", "https://s3-ap-southeast-1.amazonaws.com/ikhwan-music/Mengenal+Nabi.mp3", "Qatrunnada");
    }

    public static PlayerStore getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new PlayerStore(dispatcher);
        }
        return instance;
    }

    @Override
    @Subscribe
    public void onAction(Action action) {
        switch (action.getType()) {
            case PlayerAction.PLAYER_PLAY_SONG:
                Song song = (Song) action.getData().get(PlayerAction.KEY_SONG);
                playSong(song);
                break;
            case PlayerAction.PLAYER_PLAY_CURRENT_SONG:
                playCurrentSong();
                break;
            case PlayerAction.PLAYER_PLAY_RADIO:
                playRadio();
                break;
            case PlayerAction.PLAYER_PAUSE:
                pause();
                break;
            case PlayerAction.PLAYER_RANDOM_PLAYLIST:
                randomPlaylist();
                break;
            case PlayerAction.PLAYER_NEXT:
                next();
                break;
            case PlayerAction.PLAYER_PREVIOUS:
                previous();
                break;
            default:
                return;
        }
    }

    private void playSong(Song song) {
        AudioService.getInstance().play(song.getUrl(), this);
        currentSong = song;
        playingStatus = PlayingStatus.PLAY;
        playingMode = PlayingMode.SONG;
    }

    private void playCurrentSong() {
        AudioService.getInstance().play(currentSong.getUrl(), this);
        playingStatus = PlayingStatus.PLAY;
        playingMode = PlayingMode.SONG;
    }

    private void playRadio() {
        AudioService.getInstance().play(radioSong.getUrl(), this);
        playingStatus = PlayingStatus.PLAY;
        playingMode = PlayingMode.RADIO;
    }

    private void pause() {
        AudioService.getInstance().pause();
        playingStatus = PlayingStatus.PAUSE;
        playingMode = PlayingMode.RADIO;
        emitStoreChange(new PlayerStoreChangeEvent());
    }

    private void randomPlaylist() {
        playlist = new ArrayList<>(SongStore.getInstance(dispatcher).getSongs());
        Collections.shuffle(playlist, new Random(System.nanoTime()));
        currentSong = playlist.get(0);
        emitStoreChange(new PlayerStoreChangeEvent());
    }

    private void next() {
        playlist.add(playlist.get(0));
        playlist.remove(0);

        currentSong = playlist.get(0);
        playCurrentSong();
    }

    private void previous() {
        Song song = playlist.get(0);
        playlist.remove(playlist.size() - 1);
        playlist.add(0, song);

        currentSong = playlist.get(0);
        playCurrentSong();
    }

    @Override
    public void onAudioStarted() {
        emitStoreChange(new PlayerStoreChangeEvent());
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public PlayingStatus getPlayingStatus() {
        return playingStatus;
    }

    public PlayingMode getPlayingMode() {
        return playingMode;
    }

    public class PlayerStoreChangeEvent implements StoreChangeEvent {
    }
}
