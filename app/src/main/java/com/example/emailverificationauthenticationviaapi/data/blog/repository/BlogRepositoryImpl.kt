package com.example.emailverificationauthenticationviaapi.data.blog.repository

import com.example.emailverificationauthenticationviaapi.data.blog.remote.api.BlogApi
import com.example.emailverificationauthenticationviaapi.data.blog.remote.dto.BlogResponse
import com.example.emailverificationauthenticationviaapi.data.common.utils.WrappedListResponse
import com.example.emailverificationauthenticationviaapi.domain.blog.BlogRepository
import com.example.emailverificationauthenticationviaapi.domain.blog.entity.BlogEntity
import com.example.emailverificationauthenticationviaapi.util.BaseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BlogRepositoryImpl @Inject constructor(private val blogApi: BlogApi) : BlogRepository {
    override suspend fun getAllBlogs(): Flow<BaseResult<List<BlogEntity>, WrappedListResponse<BlogResponse>>> {
        return flow {
            val response = blogApi.getAllMyBlogs()
            if (response.isSuccessful) {
                val body = response.body()!!
                val blogEntities = mutableListOf<BlogEntity>()
                body.results?.forEach { blogResponse ->
                    blogEntities.add(
                        BlogEntity(
                            pk = blogResponse.pk,
                            username = blogResponse.username,
                            title = blogResponse.title,
                            description = blogResponse.description,
                            created = blogResponse.created
                        )
                    )
                }
            } else {
                val type = object : TypeToken<WrappedListResponse<BlogResponse>>() {}.type
                val err = Gson().fromJson<WrappedListResponse<BlogResponse>>(
                    response.errorBody()!!.charStream(), type
                )!!
                err.code = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }
}