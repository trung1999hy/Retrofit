package com.nxt.retrofit.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nxt.retrofit.R
import com.nxt.retrofit.click.OnItemClick
import com.nxt.retrofit.databinding.SongBinding
import com.nxt.retrofit.model.Music
import com.nxt.retrofit.utils.Constants.BASE_URL

class SongAdapter(var click: OnItemClick) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    private var binding: SongBinding? = null

    private var differCallBack = object : DiffUtil.ItemCallback<Music>() {
        override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem.source == newItem.source
        }

        override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAdapter.ViewHolder {
        binding = SongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: SongAdapter.ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private var binding: SongBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(music: Music) {

            binding.apply {

                tvTitle.text = music.title
                tvAlbum.text = music.album
                Glide.with(ivItemImage).load(BASE_URL + music.image).into(ivItemImage)
                tvPlay.setOnClickListener {
                    click.itemClick(adapterPosition)
                }

            }
        }
    }
}