package com.gi.ikhwanmusicandroid.stores

import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.firebase.client.ValueEventListener
import com.gi.ikhwanmusicandroid.MainActivity
import com.gi.ikhwanmusicandroid.models.Song

import java.util.ArrayList

/**
 * Created by gmochid on 2016/02/02.
 */
class SongStore(appFirebase: Firebase) {

    var ref: Firebase
        private set

    var songs: ArrayList<Song> = ArrayList()
        private set

    private var listeners: ArrayList<SongStoreListener> = ArrayList()

    init {
        ref = appFirebase.child("songs")

        callbackSetup()
    }

    private fun callbackSetup() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (songSnapshot in dataSnapshot.children) {
                    var song = songSnapshot.getValue(Song::class.java)
                    songs.add(song)
                }
                notifyUpdate()
            }

            override fun onCancelled(firebaseError: FirebaseError) {
                System.err.println(firebaseError.message)
            }
        })
    }

    fun subscribe(listener: SongStoreListener) {
        listeners.add(listener)
    }

    fun notifyUpdate() {
        for (listener in listeners) {
            listener.songsUpdated(songs)
        }
    }

    public interface SongStoreListener {
        fun songsUpdated(songs: ArrayList<Song>)
    }

}
