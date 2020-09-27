package com.example.balemusic.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocalMusicDao {
    @Insert
    suspend fun insertAll(list: List<LocalMusic>)

    @Query("SELECT * FROM musics WHERE rank LIKE :rank")
    suspend fun getSpecificMusic(rank: Int) : LocalMusic
}