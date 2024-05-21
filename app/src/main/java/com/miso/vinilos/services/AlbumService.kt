package com.miso.vinilos.services

import com.miso.vinilos.models.Album
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("albums/{albumId}")
    suspend fun getAlbum(@Path("albumId") albumId: Int): Album

    @Headers("Content-Type: application/json")
    @POST("albums")
    suspend fun createAlbum(@Body album: Album): Album
}