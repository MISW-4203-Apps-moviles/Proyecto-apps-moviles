package com.miso.vinilos.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://vynil-back-97a95a6ad966.herokuapp.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val albumService: AlbumService by lazy {
        retrofit.create(AlbumService::class.java)
    }
}