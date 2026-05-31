package com.example.demo.data.login.remote.api

import com.example.demo.data.login.remote.request.LoginRequest
import com.example.demo.data.login.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    @Headers("Accept: application/json")
    @POST("auth/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}