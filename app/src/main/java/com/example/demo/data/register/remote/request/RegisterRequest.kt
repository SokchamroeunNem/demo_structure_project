package com.example.demo.data.register.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    @SerializedName("email") val email: String,
    @SerializedName("username") val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("confirmPassword") val passwordConfirmation: String
)