package com.example.balemusic.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.balemusic.data.database.LocalMusic
import com.example.balemusic.data.database.RoomInstance
import kotlinx.coroutines.CoroutineScope

class ApiRepository( val app :Application ,val coroutineScope: CoroutineScope) {
    private val roomInstance = RoomInstance.getRoomInstance(app , coroutineScope)
    suspend fun callApi(): Response {
        return RetrofitInstance.billBoardApi.getMusicList()
    }

    suspend fun getSpecificMusic(rank:Int) : LocalMusic? {
         return roomInstance?.localMusicDao()?.getSpecificMusic(rank)
    }
}