package com.gi.ikhwanmusicandroid.stores

import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.firebase.client.ValueEventListener
import com.gi.ikhwanmusicandroid.models.Song

import java.util.ArrayList

/**
 * Created by gmochid on 2016/02/02.
 */
class SongStore(appFirebase: Firebase) {

    var ref: Firebase
        private set

    private var songs: ArrayList<Song>

    init {
        ref = appFirebase.child("songs")
        songs = ArrayList<Song>()
    }

    private fun registerCallback() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (songSnapshot in dataSnapshot.children) {
                    var song = songSnapshot.getValue(Song::class.java)
                    songs.add(song)
                }
            }

            override fun onCancelled(firebaseError: FirebaseError) {
                System.err.println(firebaseError.message)
            }
        })
    }

}
