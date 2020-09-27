package com.example.balemusic.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.balemusic.data.Music
import com.example.balemusic.view.MusicContainerFragment
import com.example.balemusic.view.MusicItemFragment

class MusicPagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {
    var mList: List<Music>? = null

    fun setData(list : List<Music>){
        mList = list
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return mList?.size?:0
    }

    override fun createFragment(position: Int): Fragment {
        return MusicItemFragment.newInstance()
    }
}