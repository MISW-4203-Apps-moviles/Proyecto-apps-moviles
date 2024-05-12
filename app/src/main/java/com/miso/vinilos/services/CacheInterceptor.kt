package com.miso.vinilos.services

import com.miso.vinilos.MainActivity.Companion.context
import com.miso.vinilos.utils.Network
import okhttp3.Interceptor
import okhttp3.Response

const val MAX_AGE = 60 * 60 * 24
const val MAX_STALE = 60 * 2

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = if (Network().isConnected(context)) {
            request.newBuilder()
                .header("Cache-Control", "public, max-age=$MAX_AGE")
                .build()
        } else {
            request.newBuilder()
                .header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=$MAX_STALE"
                )
                .build()
        }

        return chain.proceed(request)
    }
}