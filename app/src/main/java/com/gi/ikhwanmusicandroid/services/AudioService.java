package com.gi.ikhwanmusicandroid.services;

import android.media.MediaPlayer;

import com.gi.ikhwanmusicandroid.models.Song;

import java.io.IOException;

/**
 * Created by gmochid on 1/30/16.
 */
public class AudioService {

    private static AudioService audioService;

    private MediaPlayer mediaPlayer;
    private String url = "";
    private Boolean playing = false;

    private AudioService() {
        mediaPlayer = new MediaPlayer();
    }

    public static AudioService getInstance() {
        if (audioService == null) {
            audioService = new AudioService();
        }
        return audioService;
    }

    public void play(String url, final AudioServiceListener listener) {
        if (this.url.equals(url)) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
            return;
        }

        this.url = url;
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer player) {
                player.start();
                listener.onAudioStarted();
            }
        });
        playing = true;
    }

    public void pause() {
        mediaPlayer.pause();
        playing = false;
    }

    public interface AudioServiceListener {
        void onAudioStarted();
    }

}
