package com.example.emailverificationauthenticationviaapi.data.blog.remote.dto

import com.google.gson.annotations.SerializedName

data class BlogResponse(
    @SerializedName("pk") val pk: Int,
    @SerializedName("username") val username: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("created") val created: String,
    @SerializedName("updated") val updated: String?
)