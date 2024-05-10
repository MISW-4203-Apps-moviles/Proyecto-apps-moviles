package com.miso.vinilos.services

import com.miso.vinilos.models.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface PerformedService {
    //@GET("performers")
    @GET("albums")
    suspend fun getPerformers(): List<Album>

    @GET("performers/{performerId}")
    suspend fun getPerformer(@Path("performerId") performedId: Int): Album
}