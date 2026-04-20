package com.example.emailverificationauthenticationviaapi.data.login.remote.api

import com.example.emailverificationauthenticationviaapi.data.login.remote.dto.LoginRequest
import com.example.emailverificationauthenticationviaapi.data.login.remote.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    @Headers("Accept: application/json")
    @POST("auth/signin")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}