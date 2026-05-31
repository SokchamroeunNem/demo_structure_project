package com.example.demo.data.login.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") var status: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("access_token") var accessToken: String? = null,
    @SerializedName("refresh_token") var refreshToken: String? = null
)