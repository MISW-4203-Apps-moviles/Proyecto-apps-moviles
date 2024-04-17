package com.miso.vinilos.repositories

import com.miso.vinilos.models.Album
import com.miso.vinilos.services.RetrofitInstance

class AlbumRepository {
    private val albumService = RetrofitInstance.albumService

    suspend fun getAlbums(): List<Album> {
        return albumService.getAlbums()

//        return listOf(
//            Album(
//                id = "1",
//                title = "The Dark Side of the Moon",
//                artist = "Pink Floyd",
//                year = "1973",
//                imageUrl = "https://fastly.picsum.photos/id/861/200/200.jpg?hmac=UJSK-tjn1gjzSmwHWZhjpaGahNSBDQWpMoNvg8Bxy8k"
//            ),
//            Album(
//                id = "2",
//                title = "Abbey Road",
//                artist = "The Beatles",
//                year = "1969",
//                imageUrl = "https://fastly.picsum.photos/id/861/200/200.jpg?hmac=UJSK-tjn1gjzSmwHWZhjpaGahNSBDQWpMoNvg8Bxy8k"
//            ),
//        )
    }

    suspend fun getAlbum(albumId: Int): Album {
        return albumService.getAlbum(albumId)
    }
}