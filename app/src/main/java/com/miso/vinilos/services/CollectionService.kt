package com.miso.vinilos.services

import com.miso.vinilos.models.Album
import retrofit2.http.GET
import retrofit2.http.Path
interface CollectionService {
    //@GET("collectors")
    @GET("albums")
    suspend fun getCollections(): List<Album>

    @GET("collectors/{collectionId}")
    suspend fun getCollection(@Path("collectionId") collectionId: Int): Album
}