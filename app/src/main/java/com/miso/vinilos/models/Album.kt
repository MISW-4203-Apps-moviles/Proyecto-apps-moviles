package com.miso.vinilos.models

data class Album(
    val id: Int? = null,
    val name: String,
    val releaseDate: String,
    val cover: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val performers: List<Performer>? = null,
    val comments: List<Comment>? = null
)