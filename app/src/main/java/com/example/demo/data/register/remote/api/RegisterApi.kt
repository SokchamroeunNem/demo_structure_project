package com.example.demo.data.register.remote.api

import com.example.demo.data.register.remote.request.RegisterRequest
import com.example.demo.data.register.remote.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegisterApi {
    @Headers("Accept: application/json")
    @POST("auth/signup")
    suspend fun register(@Body registerRequest: RegisterRequest) : Response<RegisterResponse>
}