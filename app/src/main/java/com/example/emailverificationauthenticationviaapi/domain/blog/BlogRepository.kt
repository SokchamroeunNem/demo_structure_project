package com.example.emailverificationauthenticationviaapi.domain.blog

import com.example.emailverificationauthenticationviaapi.data.blog.remote.dto.BlogResponse
import com.example.emailverificationauthenticationviaapi.data.common.utils.WrappedListResponse
import com.example.emailverificationauthenticationviaapi.domain.blog.entity.BlogEntity
import com.example.emailverificationauthenticationviaapi.util.BaseResult
import kotlinx.coroutines.flow.Flow

interface BlogRepository {

    suspend fun getAllBlogs() : Flow<BaseResult<List<BlogEntity>, WrappedListResponse<BlogResponse>>>

}