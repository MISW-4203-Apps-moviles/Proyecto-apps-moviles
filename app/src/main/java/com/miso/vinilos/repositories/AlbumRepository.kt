package com.miso.vinilos.repositories

import com.miso.vinilos.models.Album
import com.miso.vinilos.services.RetrofitInstance

class AlbumRepository {
    private val albumService = RetrofitInstance.albumService

    suspend fun getAlbums(): List<Album> {
        return albumService.getAlbums()
    }

    suspend fun getAlbum(albumId: Int): Album {
        return albumService.getAlbum(albumId)
    }
}