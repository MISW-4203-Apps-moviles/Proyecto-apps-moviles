package com.miso.vinilos.services

import com.miso.vinilos.models.Album
import com.miso.vinilos.models.Performer
import retrofit2.http.GET
import retrofit2.http.Path

interface PerformedService {
    //@GET("performers")
    @GET("musicians")
    suspend fun getPerformers(): List<Performer>

    @GET("musicians/{performerId}")
    suspend fun getPerformer(@Path("performerId") performedId: String): Performer
}