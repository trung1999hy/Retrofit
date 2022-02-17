package com.nxt.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nxt.retrofit.adapter.SongAdapter
import com.nxt.retrofit.click.OnItemClick
import com.nxt.retrofit.databinding.ActivityMainBinding
import com.nxt.retrofit.model.Song
import com.nxt.retrofit.network.SongInstance
import retrofit2.Call
import retrofit2.Response
import android.media.MediaPlayer
import com.nxt.retrofit.utils.Constants.BASE_URL


class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var binding: ActivityMainBinding
    private lateinit var songAdapter: SongAdapter
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        songAdapter = SongAdapter(this)
        binding.rv.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

            adapter = songAdapter
            addItemDecoration(object :
                DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL) {})
        }

        fetchData()
    }

    private fun fetchData() {
        SongInstance.api.getDataFromApi().enqueue(object : retrofit2.Callback<Song> {
            override fun onResponse(call: Call<Song>, response: Response<Song>) {
                if (response.isSuccessful) {
                    val musics = response.body()?.music
                    songAdapter.differ.submitList(musics)
                }
            }

            override fun onFailure(call: Call<Song>, t: Throwable) {
                Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun itemClick(position: Int) {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            startMusic(position)
        }else{
            startMusic(position)
        }
    }

    private fun startMusic(mPosition: Int) {
        mediaPlayer = MediaPlayer()
        val url = BASE_URL + songAdapter.differ.currentList[mPosition].source
        mediaPlayer!!.setDataSource(url)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
    }
}