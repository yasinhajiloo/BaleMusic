package com.example.balemusic.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musics")
data class LocalMusic(
    @PrimaryKey(autoGenerate = false)
    val rank: Int,
    val URI: String,
)