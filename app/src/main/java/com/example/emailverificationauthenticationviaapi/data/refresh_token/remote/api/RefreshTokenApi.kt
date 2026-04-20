package com.example.emailverificationauthenticationviaapi.data.refresh_token.remote.api

import com.example.emailverificationauthenticationviaapi.data.login.remote.dto.LoginResponse
import com.example.emailverificationauthenticationviaapi.data.refresh_token.remote.dto.TokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RefreshTokenApi {
    @Headers("Accept: application/json")
    @POST("auth/token/refresh")
    suspend fun refreshToken(@Body tokenRequest: TokenRequest): Response<LoginResponse>
}