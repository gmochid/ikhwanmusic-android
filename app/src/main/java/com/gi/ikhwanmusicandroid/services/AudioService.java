package com.gi.ikhwanmusicandroid.services;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by gmochid on 1/30/16.
 */
public class AudioService {

    private static AudioService audioService;

    private MediaPlayer mediaPlayer;
    private String url = "";

    private AudioService() {
        mediaPlayer = new MediaPlayer();
    }

    public static AudioService getInstance() {
        if (audioService == null) {
            audioService = new AudioService();
        }
        return audioService;
    }

    public void play(String url) {
        if (this.url.equals(url)) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
            return;
        }

        this.url = url;
        mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("play");
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

}
