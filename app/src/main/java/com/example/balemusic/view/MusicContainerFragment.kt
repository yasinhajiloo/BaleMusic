package com.example.balemusic.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.balemusic.R
import com.example.balemusic.adapter.MusicPagerAdapter
import com.example.balemusic.data.Music
import com.example.balemusic.utils.Constants
import com.example.balemusic.utils.PlayerState
import com.example.balemusic.utils.Spotify
import com.example.balemusic.viewmodel.AppViewModel
import com.example.balemusic.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_music_container.*


class MusicContainerFragment : Fragment() {

    private var musicList: List<Music>? = null
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var appViewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        appViewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
        return inflater.inflate(R.layout.fragment_music_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pos = arguments?.getInt(Constants.MUSIC_ITEM_POS)
        val size = arguments?.getInt(Constants.LIST_SIZE_KEY)

        Toast.makeText(context, pos.toString(), Toast.LENGTH_SHORT).show()

        val pagerAdapter = MusicPagerAdapter(activity as AppCompatActivity)
        vp_music.isUserInputEnabled = false
        vp_music.adapter = pagerAdapter
        //run with delay!
        if (pos != null) {
            vp_music.post {
                vp_music.currentItem = pos
            }
        }

        iv_back.setOnClickListener {
            val backwardSize = --vp_music.currentItem
            vp_music.currentItem = backwardSize
            appViewModel.callSpecificMusic(musicList?.get(backwardSize)?.rank ?: -1)

        }
        iv_forward.setOnClickListener {
            val forwardSize = ++vp_music.currentItem
            vp_music.currentItem = forwardSize
            appViewModel.callSpecificMusic(musicList?.get(forwardSize)?.rank ?: -1)
        }

        iv_play_pause.setOnClickListener {
            when (Spotify.playerState) {
                PlayerState.PLAY -> {
                    iv_play_pause.setBackgroundResource(R.drawable.ic_play)
                    Spotify.pause()
                }
                PlayerState.PAUSE -> {
                    iv_play_pause.setBackgroundResource(R.drawable.ic_pause)
                    Spotify.resume()
                }
            }
        }

        //get all list and find current pos information
        sharedViewModel.liveDataList.observe(viewLifecycleOwner, {
            pagerAdapter.setData(it)
            appViewModel.callSpecificMusic(it[pos!!].rank)
            musicList = it
        })

        //get current pos information
        appViewModel.liveLocalMusicResult.observe(viewLifecycleOwner, {
            Spotify.play(it.URI)
        })

    }
}