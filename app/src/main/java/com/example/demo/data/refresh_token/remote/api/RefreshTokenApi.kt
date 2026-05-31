package com.example.demo.data.refresh_token.remote.api

import com.example.demo.data.login.remote.response.LoginResponse
import com.example.demo.data.refresh_token.remote.request.TokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RefreshTokenApi {
    @Headers("Accept: application/json")
    @POST("auth/token/refresh")
    suspend fun refreshToken(@Body tokenRequest: TokenRequest): Response<LoginResponse>
}