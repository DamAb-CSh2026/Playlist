package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val tracks = mutableListOf(
        Track("Bohemian Rhapsody", "Queen",       355),
        Track("Billie Jean",       "Michael Jackson", 294),
        Track("Smells Like Teen Spirit", "Nirvana", 301),
        Track("Yesterday",         "The Beatles", 125),
        Track("Lose Yourself",     "Eminem",      326),
        Track("Shape of You",      "Ed Sheeran",  233),
        Track("Blinding Lights",   "The Weeknd",  200)
    )

    private lateinit var adapter: TrackAdapter
    private lateinit var textTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listTracks)
        val editTitle = findViewById<EditText>(R.id.editTitle)
        val editArtist = findViewById<EditText>(R.id.editArtist)
        val editDuration = findViewById<EditText>(R.id.editDuration)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        textTotal = findViewById(R.id.textTotalDuration)

        adapter = TrackAdapter(this, tracks)
        listView.adapter = adapter
        updateTotalDuration()

        listView.setOnItemClickListener { _, _, position, _ ->
            val t = tracks[position]
            Toast.makeText(
                this,
                "${t.title}\nИсполнитель: ${t.artist}\nДлительность: ${TrackAdapter.formatDuration(t.durationSec)}",
                Toast.LENGTH_LONG
            ).show()
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val removed = tracks.removeAt(position)
            adapter.notifyDataSetChanged()
            updateTotalDuration()
            Toast.makeText(this, "Удалён: ${removed.title}", Toast.LENGTH_SHORT).show()
            true
        }

        buttonAdd.setOnClickListener {
            val title = editTitle.text.toString().trim()
            val artist = editArtist.text.toString().trim()
            val durStr = editDuration.text.toString().trim()

            if (title.isEmpty() || artist.isEmpty() || durStr.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val duration = durStr.toIntOrNull()
            if (duration == null || duration <= 0) {
                Toast.makeText(this, "Некорректная длительность", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            tracks.add(Track(title, artist, duration))
            adapter.notifyDataSetChanged()
            updateTotalDuration()

            editTitle.text.clear()
            editArtist.text.clear()
            editDuration.text.clear()
        }
    }

    private fun updateTotalDuration() {
        val totalSec = tracks.sumOf { it.durationSec }
        textTotal.text = "Общая длительность: ${TrackAdapter.formatDuration(totalSec)}"
    }
}