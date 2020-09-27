package com.example.balemusic.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.balemusic.data.Response
import com.example.balemusic.data.ApiRepository
import com.example.balemusic.data.Music
import com.example.balemusic.data.database.LocalMusic
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ApiRepository(application, viewModelScope)

    //get list API
    private val liveMusicLis = MutableLiveData<Response>()
    val liveResult: LiveData<Response> = liveMusicLis
    fun callForApi() {
        viewModelScope.launch {
            val data = repository.callApi()
            liveMusicLis.postValue(data)
        }
    }

    //get Specific Music by rank
    private val liveLocalMusic = MutableLiveData<LocalMusic>()
    val liveLocalMusicResult: LiveData<LocalMusic> = liveLocalMusic
    fun callSpecificMusic(rank: Int) {
        viewModelScope.launch {
            val data = repository.getSpecificMusic(rank)
            Log.d("TAGAPP2", "callSpecificMusic: ${data?.URI}")
            if (data != null)
                liveLocalMusic.postValue(data)
        }
    }
}