package com.example.balemusic.view

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.example.balemusic.R
import com.example.balemusic.utils.Constants
import com.example.balemusic.utils.Spotify
import kotlinx.android.synthetic.main.fragment_music_container.*
import kotlinx.android.synthetic.main.fragment_music_item.*


class MusicItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor =
            ContextCompat.getColor(view.context, R.color.colorPrimary)


        Spotify.getCurrentTrack {
            tv_music_artist.text = it.artist.toString()
            tv_music_name.text = it.name.toString()
        }

        Spotify.suscribeToChanges {
            Spotify.getImage(it.imageUri) {
                iv_music_bg.setImageBitmap(it)
            }
        }
    }

    companion object {
        fun newInstance() = MusicItemFragment().apply {
            arguments = Bundle().apply {
//                putInt(Constants.MUSIC_RANK_KEY, rank)
            }
        }

    }
}
