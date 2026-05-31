package com.example.demo.data.refresh_token.remote.request

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("token") val token: String
)