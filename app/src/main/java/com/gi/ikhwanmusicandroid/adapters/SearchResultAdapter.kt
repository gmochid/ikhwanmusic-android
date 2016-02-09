package com.gi.ikhwanmusicandroid.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.gi.ikhwanmusicandroid.R
import com.gi.ikhwanmusicandroid.actions.PlayerAction
import com.gi.ikhwanmusicandroid.stores.SongStore

/**
 * Created by gmochid on 2016/02/09.
 */
class SearchResultAdapter(private var songStore: SongStore, private var playerAction: PlayerAction) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_card, parent, false)
        return SearchResultViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val song = songStore.queryResult[position]
        holder.songTitle.text = song.title
        holder.songArtist.text = song.artist
        holder.songImage.setImageDrawable(ContextCompat.getDrawable(holder.context, R.drawable.ic_menu_camera))

        holder.view.setOnClickListener ({
            playerAction.playSong(song)
        })
    }

    override fun getItemCount(): Int {
        return songStore.queryResult.size
    }

    class SearchResultViewHolder(var view: View, var context: Context) : RecyclerView.ViewHolder(view) {
        var songTitle: TextView
        var songArtist: TextView
        var songImage: ImageView

        init {
            songTitle = view.findViewById(R.id.home_card_title) as TextView
            songImage = view.findViewById(R.id.home_card_image) as ImageView
            songArtist = view.findViewById(R.id.home_card_subtitle) as TextView
        }
    }
}
