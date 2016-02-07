package com.gi.ikhwanmusicandroid.stores;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
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

    protected SongStore(Dispatcher dispatcher, Firebase rootFirebase) {
        super(dispatcher);
        songs = new ArrayList<>();
        ref = rootFirebase.child("songs");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot songSnapshot : dataSnapshot.getChildren()) {
                    Song song = songSnapshot.getValue(Song.class);
                    songs.add(song);
                }
                emitStoreChange();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.err.println(firebaseError.getMessage());
            }
        });
    }

    public static SongStore getInstance(Dispatcher dispatcher, Firebase rootFirebase) {
        if (instance == null) {
            instance = new SongStore(dispatcher, rootFirebase);
        }
        return instance;
    }

    public ArrayList<Song> getSongs() {
        return songs;
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

    private void search(String query) {
        ref.equalTo(query).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                queryResult.clear();
                for (DataSnapshot songSnapshot : dataSnapshot.getChildren()) {
                    Song song = songSnapshot.getValue(Song.class);
                    queryResult.add(song);
                }
                emitStoreChange();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.err.println(firebaseError.getMessage());
            }
        });
        emitStoreChange();
    }

    @Override
    StoreChangeEvent changeEvent() {
        return new SongStoreChangeEvent();
    }

    public class SongStoreChangeEvent implements StoreChangeEvent {
    }
}
