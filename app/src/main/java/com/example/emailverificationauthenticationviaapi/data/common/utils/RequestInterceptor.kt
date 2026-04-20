package com.example.emailverificationauthenticationviaapi.data.common.utils

import android.util.Log
import com.example.emailverificationauthenticationviaapi.preferences.SharedPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

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