package com.gi.ikhwanmusicandroid.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.gi.ikhwanmusicandroid.R
import com.gi.ikhwanmusicandroid.actions.PlayerAction
import com.gi.ikhwanmusicandroid.stores.PlayerStore
import com.gi.ikhwanmusicandroid.stores.SongStore

/**
 * Created by gmochid on 1/31/16.
 */
class SongAdapter(private var songStore: SongStore, private var playerStore: PlayerStore, private var playerAction: PlayerAction) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_card, parent, false)
        return SongViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songStore.songs[position]
        holder.songTitle.text = song.title
        holder.songArtist.text = song.artist
        holder.songImage.setImageDrawable(ContextCompat.getDrawable(holder.context, R.drawable.ic_menu_camera))
        holder.playProgressBar.visibility = View.GONE

        if (playerStore.currentSong.equals(song)) {
            holder.playStatusImage.visibility =
                    if (playerStore.isPlaying) View.VISIBLE else View.GONE
            holder.pauseStatusImage.visibility =
                    if (playerStore.isPlaying) View.GONE else View.VISIBLE

            holder.view.setOnClickListener ({
                if (playerStore.isPlaying)
                    playerAction.pause()
                else
                    playerAction.playCurrentSong()
            })
        } else {
            holder.pauseStatusImage.visibility = View.GONE
            holder.playStatusImage.visibility = View.GONE
            holder.view.setOnClickListener ({
                holder.playProgressBar.visibility = View.VISIBLE
                playerAction.playSong(song)
            })
        }
    }

    override fun getItemCount(): Int {
        return songStore.songs.size
    }

    class SongViewHolder(var view: View, var context: Context) : RecyclerView.ViewHolder(view) {
        var songTitle: TextView
        var songArtist: TextView
        var songImage: ImageView
        var playProgressBar: ProgressBar
        var playStatusImage: ImageView
        var pauseStatusImage: ImageView

        init {
            songTitle = view.findViewById(R.id.home_card_title) as TextView
            songImage = view.findViewById(R.id.home_card_image) as ImageView
            songArtist = view.findViewById(R.id.home_card_subtitle) as TextView
            playProgressBar = view.findViewById(R.id.play_progress_bar) as ProgressBar
            playStatusImage = view.findViewById(R.id.play_status_image) as ImageView
            pauseStatusImage = view.findViewById(R.id.pause_status_image) as ImageView
        }
    }
}
