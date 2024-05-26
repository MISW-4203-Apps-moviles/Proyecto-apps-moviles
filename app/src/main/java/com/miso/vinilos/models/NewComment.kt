package com.miso.vinilos.models

data class NewCollector(val id: Int)
data class NewComment(
    val description: String,
    val rating: Int,
    val collector: NewCollector
)