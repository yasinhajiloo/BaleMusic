package com.example.balemusic.utils

interface SpotifyConnectionState {
    fun onSuccess()
    fun onFailure(error : String?)
}