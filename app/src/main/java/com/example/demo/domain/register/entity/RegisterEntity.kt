package com.example.demo.domain.register.entity

data class RegisterEntity(
    val status: String,
    val message: String,
    val accessToken: String,
    val refreshToken: String
)