package com.nxt.retrofit.model

data class Song(val music: List<Music>)


data class Music(val image: String, val title: String, val album: String, val source: String)
