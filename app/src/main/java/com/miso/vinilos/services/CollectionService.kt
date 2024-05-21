package com.miso.vinilos.services

import com.miso.vinilos.models.Collector
import retrofit2.http.GET
import retrofit2.http.Path
interface CollectionService {
    //@GET("collectors")
    @GET("collectors")
    suspend fun getCollections(): List<Collector>

    @GET("collectors/{collectionId}")
    suspend fun getCollector(@Path("collectionId") collectionId: String): Collector
}