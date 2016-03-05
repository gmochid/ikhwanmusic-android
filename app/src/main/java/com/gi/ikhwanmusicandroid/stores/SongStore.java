package com.gi.ikhwanmusicandroid.stores;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gi.ikhwanmusicandroid.BuildConfig;
import com.gi.ikhwanmusicandroid.actions.Action;
import com.gi.ikhwanmusicandroid.actions.Dispatcher;
import com.gi.ikhwanmusicandroid.actions.PlayerAction;
import com.gi.ikhwanmusicandroid.actions.SongAction;
import com.gi.ikhwanmusicandroid.models.Song;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by gmochid on 2/4/16.
 */
public class SongStore extends Store {
    private static SongStore instance = null;
    private ArrayList<Song> songs;
    private ArrayList<Song> queryResult;
    private Firebase ref;

    protected SongStore(final Dispatcher dispatcher) {
        super(dispatcher);
        songs = new ArrayList<>();
        queryResult = new ArrayList<>();
        ref = new Firebase(BuildConfig.FIREBASE_URL).child("songs");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot songSnapshot : dataSnapshot.getChildren()) {
                    Song song = songSnapshot.getValue(Song.class);
                    songs.add(song);
                }
                PlayerAction.getInstance(dispatcher).generateRandomPlaylist();
                emitStoreChange(new SongStoreChangeEvent());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.err.println(firebaseError.getMessage());
            }
        });
    }

    public static SongStore getInstance(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new SongStore(dispatcher);
        }
        return instance;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public ArrayList<Song> getQueryResult() {
        return queryResult;
    }

    @Override
    @Subscribe
    public void onAction(Action action) {
        switch (action.getType()) {
            case SongAction.SONG_SEARCH:
                String query = (String) action.getData().get(SongAction.KEY_QUERY);
                search(query);
                break;
        }
    }

    private void search(final String query) {
        queryResult.clear();
        for(Song song : songs) {
            if(song.getTitle().toLowerCase().matches(String.format(".*%s.*", query.toLowerCase()))) {
                queryResult.add(song);
            }
        }
        emitStoreChange(new SongStoreSearchChangeEvent());
    }

    public class SongStoreChangeEvent implements StoreChangeEvent {
    }

    public class SongStoreSearchChangeEvent implements StoreChangeEvent {
    }
}
