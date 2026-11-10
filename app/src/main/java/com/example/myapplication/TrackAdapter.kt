package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class TrackAdapter(
    context: Context,
    private val tracks: List<Track>
) : ArrayAdapter<Track>(context, 0, tracks) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater
            .inflate(R.layout.item_track, parent, false)
            .also { it.tag = ViewHolder(it) }

        val holder = view.tag as ViewHolder

        getItem(position)?.let { track ->
            holder.title.text = track.title
            holder.artist.text = track.artist
            holder.duration.text = formatDuration(track.durationSec)
            holder.note.setImageResource(android.R.drawable.ic_media_play)
        }
        return view
    }

    private class ViewHolder(view: View) {
        val note: ImageView = view.findViewById(R.id.imageNote)
        val title: TextView = view.findViewById(R.id.textTitle)
        val artist: TextView = view.findViewById(R.id.textArtist)
        val duration: TextView = view.findViewById(R.id.textDuration)
    }

    companion object {
        fun formatDuration(sec: Int): String {
            val m = sec / 60
            val s = sec % 60
            return "%d:%02d".format(m, s)
        }
    }
}