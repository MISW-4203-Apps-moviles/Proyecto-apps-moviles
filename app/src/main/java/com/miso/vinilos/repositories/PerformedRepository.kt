package com.miso.vinilos.repositories

import com.miso.vinilos.models.Album
import com.miso.vinilos.services.RetrofitInstance

class PerformedRepository {
    private val performedService = RetrofitInstance.performedService

    suspend fun getPerformers(): List<Album> {
        return performedService.getPerformers()
    }

    suspend fun getPerformer(performedId: Int): Album {
        return performedService.getPerformer(performedId)
    }
}