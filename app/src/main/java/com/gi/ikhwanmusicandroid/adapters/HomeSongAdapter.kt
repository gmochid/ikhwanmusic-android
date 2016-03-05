package com.gi.ikhwanmusicandroid.adapters

import com.gi.ikhwanmusicandroid.actions.PlayerAction
import com.gi.ikhwanmusicandroid.models.Song
import com.gi.ikhwanmusicandroid.stores.PlayerStore
import com.gi.ikhwanmusicandroid.stores.SongStore

/**
 * Created by gmochid on 3/5/16.
 */
class HomeSongAdapter (private var songStore: SongStore, private var playerStore: PlayerStore, private var playerAction: PlayerAction) : SongAdapter(songStore, playerStore, playerAction) {

    override fun getItemCount(): Int {
        return songStore.songs.size
    }

    override fun getSong(position: Int): Song {
        return songStore.songs[position]
    }
}
