package com.miso.vinilos.models

data class Album(
    val id: Int,
    val name: String,
    val releaseDate: String,
    val cover: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val performers: List<Performer>,
    val comments: List<Comment>
)