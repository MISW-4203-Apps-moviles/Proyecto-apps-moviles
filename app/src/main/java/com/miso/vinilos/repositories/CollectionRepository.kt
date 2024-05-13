package com.miso.vinilos.repositories

import com.miso.vinilos.models.Collector
import com.miso.vinilos.services.RetrofitInstance

class CollectionRepository {
    private val collectionService = RetrofitInstance.collectionService

    suspend fun getCollections(): List<Collector> {
        return collectionService.getCollections()
    }

    suspend fun getCollection(collectionId: Int): Collector {
        return collectionService.getCollection(collectionId)
    }
}