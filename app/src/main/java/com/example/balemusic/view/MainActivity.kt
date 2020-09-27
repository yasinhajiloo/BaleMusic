package com.example.balemusic.view

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.balemusic.R
import com.example.balemusic.utils.Spotify
import com.example.balemusic.utils.SpotifyConnectionState
import com.spotify.android.appremote.api.SpotifyAppRemote

class MainActivity : AppCompatActivity(), SpotifyConnectionState {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var alertDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Connect Bale to Spotify")
        builder.setMessage("Please wait ...")
        builder.setCancelable(false)
        alertDialog = builder.create()

        if (SpotifyAppRemote.isSpotifyInstalled(this)) {
            alertDialog.show()
            Spotify.connect(this, this)
        } else
            Toast.makeText(this, "You hav not Installed Spotify yet!", Toast.LENGTH_SHORT).show()

    }

    override fun onStop() {
        super.onStop()
        Spotify.disconnect()
    }

    override fun onSuccess() {
        alertDialog.dismiss()
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(error: String?) {
        alertDialog.dismiss()
        Toast.makeText(this, error ?: "error occurred", Toast.LENGTH_SHORT).show()
    }
}