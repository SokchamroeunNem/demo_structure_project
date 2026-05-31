package com.example.demo.domain.post.use_case


import com.example.demo.data.common.utils.WrappedResponse
import com.example.demo.data.post.remote.response.PostResponse
import com.example.demo.domain.post.PostRepository
import com.example.demo.domain.post.entity.Post
import com.example.demo.util.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke():
            Flow<BaseResult<List<Post>, WrappedResponse<PostResponse>>> {
        return postRepository.getAllPosts()
    }
}