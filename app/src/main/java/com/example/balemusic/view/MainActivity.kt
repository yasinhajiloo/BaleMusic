package com.example.balemusic.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.balemusic.R
import com.example.balemusic.utils.Spotify
import com.spotify.android.appremote.api.SpotifyAppRemote

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (SpotifyAppRemote.isSpotifyInstalled(this))
            Spotify.connect(this)
        else
            Toast.makeText(this, "You hav not Installed Spotify yet!", Toast.LENGTH_SHORT).show()

    }

    override fun onStop() {
        super.onStop()
        Spotify.disconnect()
    }
}