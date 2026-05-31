package com.example.demo.data.post.repository

import com.example.demo.data.common.utils.WrappedResponse
import com.example.demo.data.post.mapper.toDomain
import com.example.demo.data.post.remote.api.PostApi
import com.example.demo.data.post.remote.response.PostResponse
import com.example.demo.domain.post.PostRepository
import com.example.demo.domain.post.entity.Post
import com.example.demo.util.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postApi: PostApi) : PostRepository {
    override suspend fun getAllPosts():
            Flow<BaseResult<List<Post>, WrappedResponse<PostResponse>>> =
        flow {

            try {

                val response = postApi.getPosts()

                emit(
                    BaseResult.Success(
                        response.map { it.toDomain() }
                    )
                )

            } catch (e: HttpException) {
                emit(
                    BaseResult.Error(
                        WrappedResponse(
                            message = e.message(),
                            code = e.code()
                        )
                    )
                )

            } catch (e: Exception) {

                emit(
                    BaseResult.Error(
                        WrappedResponse(
                            message = e.message
                        )
                    )
                )
            }
        }
}