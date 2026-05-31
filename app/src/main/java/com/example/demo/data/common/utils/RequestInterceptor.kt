package com.example.demo.data.common.utils

import com.example.demo.preferences.SharedPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(private val pref: SharedPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        /*val token = runBlocking {
            pref.getToken()?.first()

        }*/
        val token = runBlocking(Dispatchers.IO) {
            pref.getToken()
        }
        val newRequest = chain.request().newBuilder()
//            .addHeader("Authorization", token)
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}