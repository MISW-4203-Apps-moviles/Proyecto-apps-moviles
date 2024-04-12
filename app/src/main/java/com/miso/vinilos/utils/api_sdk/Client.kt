package com.miso.vinilos.utils.api_sdk

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class Client {
    private val client: OkHttpClient = OkHttpClient()
    private val baseUrl = "https://api.discogs.com"

    @Throws(IOException::class)
    fun get(url: String): String  {
        val request = Request.Builder()
            .url("$baseUrl$url")
            .build()

        try {
            val response = client.newCall(request).execute()
            return response.body?.string() ?: ""
        } catch (e: IOException) {
            throw e
        }
    }
}