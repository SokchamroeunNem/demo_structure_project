package com.example.demo.util

object AppSecretManager {
    init {
        System.loadLibrary("secrets")
    }

    external fun getApiBaseUrl(): String
}