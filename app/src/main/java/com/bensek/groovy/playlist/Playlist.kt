package com.bensek.groovy.playlist

import com.bensek.groovy.R

data class Playlist(
    val id: String,
    val name: String,
    val category: String,
    val image: Int = R.mipmap.playlist
)
