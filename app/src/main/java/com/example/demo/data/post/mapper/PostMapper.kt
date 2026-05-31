package com.example.demo.data.post.mapper

import com.example.demo.data.post.remote.response.PostResponse
import com.example.demo.domain.post.entity.Post

fun PostResponse.toDomain(): Post {
    return Post(
        id = id, title = title, body = body
    )
}