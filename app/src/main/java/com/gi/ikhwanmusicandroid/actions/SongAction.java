package com.gi.ikhwanmusicandroid.actions;

/**
 * Created by gmochid on 2/6/16.
 */
public class SongAction {
    public static final String SONG_SEARCH = "song_search";

    public static final String KEY_QUERY = "key_query";

    private static SongAction songAction;
    private Dispatcher dispatcher;

    SongAction(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public static SongAction getInstance(Dispatcher dispatcher) {
        if (songAction == null) {
            songAction = new SongAction(dispatcher);
        }
        return songAction;
    }

    public void search(String query) {
        dispatcher.dispatch(SONG_SEARCH,
                KEY_QUERY, query);
    }
}
