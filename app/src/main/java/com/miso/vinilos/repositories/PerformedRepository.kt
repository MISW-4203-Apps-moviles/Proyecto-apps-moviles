package com.miso.vinilos.repositories

import com.miso.vinilos.models.Performer
import com.miso.vinilos.services.RetrofitInstance

class PerformedRepository {
    private val performedService = RetrofitInstance.performedService

    suspend fun getPerformers(): List<Performer> {
        return performedService.getPerformers()
    }

    suspend fun getPerformer(performedId: Int): Performer {
        return performedService.getPerformer(performedId)
    }
}