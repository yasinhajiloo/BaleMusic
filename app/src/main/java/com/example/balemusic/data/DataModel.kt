package com.example.balemusic.data

import com.squareup.moshi.Json

data class Music(
    @field:Json(name = "rank") val rank: Int,
    @field:Json(name = "artist") val artist: String,
    @field:Json(name = "title") val title: String
)

data class MusicList(
    @field:Json(name = "1") val one: Music,
    @field:Json(name = "2") val two: Music,
    @field:Json(name = "3") val three: Music,
    @field:Json(name = "4") val four: Music,
    @field:Json(name = "5") val five: Music,
    @field:Json(name = "6") val six: Music,
    @field:Json(name = "7") val seven: Music,
    @field:Json(name = "8") val eight: Music,
    @field:Json(name = "9") val nine: Music,
    @field:Json(name = "10") val ten: Music,
) {
    fun getMusicList(): MutableList<Music> {
        return mutableListOf(one, two, three, four, five, six, seven, eight , nine , ten)
    }
}

data class Response(@field:Json(name = "content") val content: MusicList)