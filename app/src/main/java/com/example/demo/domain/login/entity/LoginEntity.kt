package com.example.demo.domain.login.entity

data class LoginEntity(
    val status: String,
    val message: String,
    val accessToken: String,
    val refreshToken: String
)