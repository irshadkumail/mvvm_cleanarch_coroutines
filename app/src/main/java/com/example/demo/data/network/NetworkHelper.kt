package com.example.demo.data.network

import okhttp3.*
import javax.inject.Inject

class NetworkHelper @Inject constructor(private val okHttpClient: OkHttpClient) {

    fun pingUrl(url: String): Response {
        val request = Request.Builder()
            .url("https://" + url)
            .build();
        return okHttpClient.newCall(request).execute();
    }
}