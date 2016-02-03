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
    private Song song = new Song("Mengenal Nabi", "https://s3-ap-southeast-1.amazonaws.com/ikhwan-music/Mengenal+Nabi.mp3", "Qatrunnada");;
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

    public void play(Song song) {
        if (this.song.getUrl().equals(song.getUrl())) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
            return;
        }

        this.song = song;
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(song.getUrl());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("play");
        mediaPlayer.start();
        playing = true;
    }

    public void pause() {
        mediaPlayer.pause();
        playing = false;
    }

    public Song getCurrentSong() {
        return song;
    }

}
