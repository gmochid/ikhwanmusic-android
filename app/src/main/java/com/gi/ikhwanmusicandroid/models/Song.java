package com.gi.ikhwanmusicandroid.models;

/**
 * Created by gmochid on 1/31/16.
 */
public class Song {
    private String title;
    private String url;

    public Song(String title, String url) {
        this.title = title;
        this.url = url;
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

}
