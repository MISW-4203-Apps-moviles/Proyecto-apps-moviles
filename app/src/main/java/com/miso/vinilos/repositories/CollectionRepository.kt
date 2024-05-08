package com.miso.vinilos.repositories

import com.miso.vinilos.models.Album
import com.miso.vinilos.services.RetrofitInstance

class CollectionRepository {
    private val collectionService = RetrofitInstance.collectionService

    suspend fun getCollections(): List<Album> {
        return collectionService.getCollections()
    }

    suspend fun getCollection(collectionId: Int): Album {
        return collectionService.getCollection(collectionId)
    }
}