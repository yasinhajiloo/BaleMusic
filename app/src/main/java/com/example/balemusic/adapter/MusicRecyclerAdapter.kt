package com.example.balemusic.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.balemusic.R
import com.example.balemusic.data.Music
import com.example.balemusic.utils.RecyclerClickHandler
import com.example.balemusic.viewmodel.AppViewModel

class MusicRecyclerAdapter(val clickInterface: RecyclerClickHandler) :
    RecyclerView.Adapter<MusicRecyclerAdapter.MyViewHolder>() {
    private var mList: List<Music>? = null;

    fun setUpData(list: List<Music>?) {
        if (list != null) {
            mList = list
            notifyDataSetChanged()
        }
    }

    fun shuffle(): List<Music>? {
        mList = mList?.shuffled()
        notifyDataSetChanged()
        return mList
    }

    fun filter(rank: Int): List<Music>? {
        mList = mList?.filter { it.rank == rank }
        notifyDataSetChanged()
        return mList
    }

    fun sort(): List<Music>? {
        mList = mList?.sortedBy { it.title }
        notifyDataSetChanged()
        return mList
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var tvArtist: TextView = view.findViewById(R.id.tv_item_artist)
        var tvTitle: TextView = view.findViewById(R.id.tv_item_title)
        var tvRank: TextView = view.findViewById(R.id.tv_rank)

        init {
            view.setOnClickListener(this)
        }

        fun bindData(music: Music) {
            tvRank.text = music.rank.toString()
            tvTitle.text = music.title
            tvArtist.text = music.artist

            //generate random color for rank bg
            val random = java.util.Random()
            val intColor =
                Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))

        }

        override fun onClick(v: View?) {
            clickInterface.onClick(adapterPosition, mList?.size ?: 0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_music, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        mList?.get(position)?.let { holder.bindData(it) }
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }
}