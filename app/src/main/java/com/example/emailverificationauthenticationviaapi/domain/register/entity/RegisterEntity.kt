package com.example.emailverificationauthenticationviaapi.domain.register.entity

data class RegisterEntity(
    val status: String,
    val message: String,
    val accessToken: String,
    val refreshToken: String
)