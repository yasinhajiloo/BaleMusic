package com.example.balemusic.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.balemusic.R
import com.example.balemusic.adapter.MusicRecyclerAdapter
import com.example.balemusic.data.Music
import com.example.balemusic.utils.Constants
import com.example.balemusic.utils.RecyclerClickHandler
import com.example.balemusic.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import java.lang.Exception


class SearchFragment : Fragment(), RecyclerClickHandler {

    private var mlist: List<Music>? = null
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var searchAdapter: MusicRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchAdapter = MusicRecyclerAdapter(this)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_music_search.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        sv_main.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    try {
                        if (newText == "") {
                            searchAdapter.setUpData(sharedViewModel.liveDataList.value)
                        }else{
                            mlist = searchAdapter.filter(newText.toInt())
                        }
                    } catch (e: Exception) {
                        Log.d("TAG", "onQueryTextChange: ${e.message}")
                    }
                    return false
                }
            })

        sharedViewModel.liveDataList.observe(viewLifecycleOwner, {
            searchAdapter.setUpData(it)
        })
    }

    override fun onClick(position: Int, wholeSize: Int) {
        sharedViewModel.setLiveList(mlist)
        val bundle = Bundle().apply {
            putInt(Constants.MUSIC_ITEM_POS, position)
            putInt(Constants.LIST_SIZE_KEY, wholeSize)
        }
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_searchFragment_to_musicFragment, bundle)
        }
    }
}