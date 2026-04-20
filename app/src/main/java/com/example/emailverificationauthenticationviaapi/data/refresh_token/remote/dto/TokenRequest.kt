package com.example.emailverificationauthenticationviaapi.data.refresh_token.remote.dto

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("token") val token: String
)