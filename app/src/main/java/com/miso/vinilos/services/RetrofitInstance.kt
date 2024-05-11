package com.miso.vinilos.services

import com.google.gson.GsonBuilder
import com.miso.vinilos.MainActivity.Companion.context
import com.miso.vinilos.utils.Network
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


object RetrofitInstance {
    private const val BASE_URL = "https://vynil-back-97a95a6ad966.herokuapp.com/"
    private val retrofit: Retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()

        httpClient.connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .cache(
                Cache(
                    File(context.cacheDir, "http-cache"),
                    10L * 1024L * 1024L
                )
            )
            .addInterceptor(CacheInterceptor())
            .build()

        httpClient.addInterceptor(logging)


        val gson = GsonBuilder().setLenient().create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    val albumService: AlbumService by lazy {
        retrofit.create(AlbumService::class.java)
    }
}
