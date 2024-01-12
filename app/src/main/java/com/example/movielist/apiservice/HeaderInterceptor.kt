package com.example.movielist.apiservice

import com.example.movielist.apiservice.ApiConst.APPLICATION_JSON
import com.example.movielist.apiservice.ApiConst.AUTHORIZATION
import com.example.movielist.apiservice.ApiConst.AUTH_TOKEN
import com.example.movielist.apiservice.ApiConst.BEARER
import com.example.movielist.apiservice.ApiConst.BEARER_TOKEN
import com.example.movielist.apiservice.ApiConst.CONTENT_TYPE
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        try {
            chain.run {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .addHeader(AUTHORIZATION, "$BEARER $BEARER_TOKEN")
                        .build()
                )
            }
        } catch (e: Exception) {
            chain.run {
                proceed(
                    request().newBuilder().build()
                )
            }
        }
    }
}