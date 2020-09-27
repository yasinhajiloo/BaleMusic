package com.example.balemusic.utils

import android.content.Context
import android.graphics.Bitmap
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.ImageUri
import com.spotify.protocol.types.Track

object Spotify {
    private val CLIENT_ID = "53adb4be4e314fbf972ad8f9b8aa17a9"
    private val REDIRECT_URI = "com.example.balemusic://callback"
    var playerState = PlayerState.PLAY


    private var spotifyAppRemote: SpotifyAppRemote? = null
    private var connectionParams: ConnectionParams = ConnectionParams.Builder(CLIENT_ID)
        .setRedirectUri(REDIRECT_URI)
        .showAuthView(true)
        .build()

    fun connect(context: Context) {
        if (spotifyAppRemote?.isConnected == true) {
            return
        }
        val connectionListener = object : Connector.ConnectionListener {
            override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                this@Spotify.spotifyAppRemote = spotifyAppRemote
            }

            override fun onFailure(throwable: Throwable) {
            }
        }
        SpotifyAppRemote.connect(context, connectionParams, connectionListener)
    }

    fun play(uri: String) {
        spotifyAppRemote?.playerApi?.play(uri)
        playerState = PlayerState.PLAY
    }

    fun resume() {
        spotifyAppRemote?.playerApi?.resume()
        playerState = PlayerState.PLAY
    }

    fun pause() {
        spotifyAppRemote?.playerApi?.pause()
        playerState = PlayerState.PAUSE
    }

    fun suscribeToChanges(handler: (Track) -> Unit) {
        spotifyAppRemote?.playerApi?.subscribeToPlayerState()?.setEventCallback {
            handler(it.track)
        }
    }

    fun getCurrentTrack(handler: (track: Track) -> Unit) {
        spotifyAppRemote?.playerApi?.playerState?.setResultCallback { result ->
            handler(result.track)
        }
    }

    fun getImage(imageUri: ImageUri, handler: (Bitmap) -> Unit) {
        spotifyAppRemote?.imagesApi?.getImage(imageUri)?.setResultCallback {
            handler(it)
        }
    }

    fun getCurrentTrackImage(handler: (Bitmap) -> Unit) {
        getCurrentTrack {
            getImage(it.imageUri) {
                handler(it)
            }
        }
    }

    fun disconnect() {
        SpotifyAppRemote.disconnect(spotifyAppRemote)
    }
}