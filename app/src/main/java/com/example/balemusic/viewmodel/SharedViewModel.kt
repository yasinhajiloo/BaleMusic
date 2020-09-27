package com.example.balemusic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.balemusic.data.Music

class SharedViewModel : ViewModel() {
    //handle data between fragments

    //pass list between home and music container
    private val liveList = MutableLiveData<List<Music>>()
    val liveDataList: LiveData<List<Music>> = liveList
    fun setLiveList(list: List<Music>?) {
        liveList.value = list
    }
}