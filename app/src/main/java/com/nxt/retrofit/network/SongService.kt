package com.nxt.retrofit.network

import com.nxt.retrofit.model.Song
import retrofit2.Call
import retrofit2.http.GET

interface SongService {

    @GET("music.json")
    fun getDataFromApi(): Call<Song>
}