package com.example.emailverificationauthenticationviaapi.data.refresh_token.remote.dto

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("status") var status: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("refresh_token") var refreshToken: String? = null
)