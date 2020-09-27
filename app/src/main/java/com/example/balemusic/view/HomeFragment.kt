package com.example.balemusic.view

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.balemusic.R
import com.example.balemusic.adapter.MusicRecyclerAdapter
import com.example.balemusic.data.Music
import com.example.balemusic.utils.Constants
import com.example.balemusic.utils.RecyclerClickHandler
import com.example.balemusic.viewmodel.AppViewModel
import com.example.balemusic.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.Comparator


class HomeFragment : Fragment(), RecyclerClickHandler {

    private var mList: MutableList<Music>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val rvAdapter = MusicRecyclerAdapter(this)


        rv_music.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        tb_home.inflateMenu(R.menu.main_menu)
        tb_home.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_sort -> {
                    sharedViewModel.setLiveList(rvAdapter.sort())
                    true
                }
                R.id.menu_shuffle -> {
                    sharedViewModel.setLiveList(rvAdapter.shuffle())
                    true
                }
                R.id.menu_search -> {
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_homeFragment_to_searchFragment)
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }
        //first call for getting list
        viewModel.callForApi()

        //get API call data
        viewModel.liveResult.observe(viewLifecycleOwner, {
            pg_home.visibility = View.GONE
            mList = it.content.getMusicList()
            rvAdapter.setUpData(mList)
            sharedViewModel.setLiveList(mList!!)
        })
    }


    override fun onClick(position: Int, wholeSize: Int) {
        val bundle = Bundle().apply {
            putInt(Constants.MUSIC_ITEM_POS, position)
            putInt(Constants.LIST_SIZE_KEY, wholeSize)
        }
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_musicFragment, bundle)
        }
    }

}