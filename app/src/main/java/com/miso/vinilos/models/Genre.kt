package com.miso.vinilos.models

enum class Genre(val displayName: String) {
    CLASSICAL("Classical"),
    SALSA("Salsa"),
    ROCK("Rock"),
    FOLK("Folk");

    companion object {
        val getGenres = entries
    }
}