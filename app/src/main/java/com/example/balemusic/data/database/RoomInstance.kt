package com.example.balemusic.data.database

import android.app.Application
import android.content.res.Resources
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Database(entities = [LocalMusic::class], version = 1)
abstract class RoomInstance : RoomDatabase() {


    abstract fun localMusicDao(): LocalMusicDao

    private class MusicDatabaseCallback(
        private val scope: CoroutineScope,
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val dao = database.localMusicDao()
                    prePopulateDatabase(dao)
                }
            }
        }

        private suspend fun prePopulateDatabase(dao: LocalMusicDao) {
            val list = listOf(
                LocalMusic(1, "spotify:track:6jmZlyf9DxcIoRrjw02YXm"),
                LocalMusic(2, "spotify:track:2Rk4JlNc2TPmZe2af99d45"),
                LocalMusic(3, "spotify:track:7xQAfvXzm3AkraOtGPWIZg"),
                LocalMusic(4, "spotify:track:22vgEDb5hykfaTwLuskFGD"),
                LocalMusic(5, "spotify:track:3KkXRkHbMCARz0aVfEt68P"),
                LocalMusic(6, "spotify:track:6ocbgoVGwYJhOv1GgI9NsF"),
                LocalMusic(7, "spotify:track:5p7ujcrUXASCNwRaWNHR1C"),
                LocalMusic(8, "spotify:track:6Qs4SXO9dwPj5GKvVOv8Ki"),
                LocalMusic(9 , "spotify:track:2Fxmhks0bxGSBdJ92vM42m"),
                LocalMusic(10 , "spotify:track:0rTV5WefWd1J3OwIheTzxM")
            )
            dao.insertAll(list)
        }
    }

    companion object {
        var INSTANCE: RoomInstance? = null

        fun getRoomInstance(
            application: Application,
            coroutineScope: CoroutineScope
        ): RoomInstance? {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            application,
                            RoomInstance::class.java,
                            "AppDatabase"
                        )
                            .addCallback(MusicDatabaseCallback(coroutineScope))
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}