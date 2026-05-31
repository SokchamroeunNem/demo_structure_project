package com.example.demo.domain.post

import com.example.demo.data.post.remote.response.PostResponse
import com.example.demo.data.common.utils.WrappedResponse
import com.example.demo.domain.post.entity.Post
import com.example.demo.util.BaseResult
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getAllPosts(): Flow<BaseResult<List<Post>, WrappedResponse<PostResponse>>>
}