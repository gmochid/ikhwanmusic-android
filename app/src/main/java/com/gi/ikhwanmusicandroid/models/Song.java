package com.gi.ikhwanmusicandroid.models;

import java.io.Serializable;

/**
 * Created by gmochid on 1/31/16.
 */
public class Song implements Serializable {
    private String title;
    private String url;
    private String artist;

    public Song() {

    }

    public Song(String title, String url, String artist) {
        this.title = title;
        this.url = url;
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
