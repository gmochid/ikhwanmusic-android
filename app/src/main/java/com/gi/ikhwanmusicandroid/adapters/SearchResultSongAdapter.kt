package com.gi.ikhwanmusicandroid.adapters

import com.gi.ikhwanmusicandroid.actions.PlayerAction
import com.gi.ikhwanmusicandroid.models.Song
import com.gi.ikhwanmusicandroid.stores.PlayerStore
import com.gi.ikhwanmusicandroid.stores.SongStore

/**
 * Created by gmochid on 2016/02/09.
 */
class SearchResultSongAdapter(private var songStore: SongStore, private var playerStore: PlayerStore, private var playerAction: PlayerAction) : SongAdapter(songStore, playerStore, playerAction) {

    override fun getItemCount(): Int {
        return songStore.queryResult.size
    }

    override fun getSong(position: Int): Song {
        return songStore.queryResult[position]
    }
}
