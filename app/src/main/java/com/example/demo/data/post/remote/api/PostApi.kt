package com.example.demo.data.post.remote.api

import com.example.demo.data.post.remote.response.PostResponse
import retrofit2.http.GET

interface PostApi {
    @GET("posts")
    suspend fun getPosts(): List<PostResponse>
}