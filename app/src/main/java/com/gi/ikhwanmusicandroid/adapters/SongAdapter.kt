package com.gi.ikhwanmusicandroid.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.firebase.client.ValueEventListener
import com.gi.ikhwanmusicandroid.MainActivity

import com.gi.ikhwanmusicandroid.R
import com.gi.ikhwanmusicandroid.models.Song
import java.util.*

/**
 * Created by gmochid on 1/31/16.
 */
class SongAdapter(private var ref: Firebase) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var songs: ArrayList<Song> = ArrayList()

    init {
        ref.child("songs").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (songSnapshot in dataSnapshot.children) {
                    var song = songSnapshot.getValue(Song::class.java)
                    songs.add(song)
                    notifyItemInserted(songs.size - 1)
                }
            }

            override fun onCancelled(firebaseError: FirebaseError) {
                System.err.println(firebaseError.message)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_card, parent, false)
        return SongViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.songTitle.text = song.title
        holder.songArtist.text = song.artist
        holder.songImage.setImageDrawable(ContextCompat.getDrawable(holder.context, R.drawable.ic_menu_camera))

        holder.view.setOnClickListener ({
            (holder.context as MainActivity).playSong(song)
        })
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    class SongViewHolder(var view: View, var context: Context) : RecyclerView.ViewHolder(view) {
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
