package com.miso.vinilos.repositories

import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Comment
import com.miso.vinilos.models.NewComment
import com.miso.vinilos.services.RetrofitInstance

class AlbumRepository {
    private val albumService = RetrofitInstance.albumService

    suspend fun getAlbums(): List<Album> {
        return albumService.getAlbums()
    }

    suspend fun getAlbum(albumId: Int): Album {
        return albumService.getAlbum(albumId)
    }


    suspend fun postComment(albumId: Int, comment: NewComment): Comment {
        return albumService.postComment(albumId, comment)
    }
    
    suspend fun createAlbum(album: Album): Album {
        return albumService.createAlbum(album)
    }
}