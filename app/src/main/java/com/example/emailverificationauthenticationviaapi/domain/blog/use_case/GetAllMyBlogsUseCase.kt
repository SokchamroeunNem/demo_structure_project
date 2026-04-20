package com.example.emailverificationauthenticationviaapi.domain.blog.use_case


import com.example.emailverificationauthenticationviaapi.data.blog.remote.dto.BlogResponse
import com.example.emailverificationauthenticationviaapi.data.common.utils.WrappedListResponse
import com.example.emailverificationauthenticationviaapi.domain.blog.BlogRepository
import com.example.emailverificationauthenticationviaapi.domain.blog.entity.BlogEntity
import com.example.emailverificationauthenticationviaapi.util.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMyBlogsUseCase @Inject constructor(private val blogRepository: BlogRepository) {
    suspend fun invoke(): Flow<BaseResult<List<BlogEntity>, WrappedListResponse<BlogResponse>>> {
        return blogRepository.getAllBlogs()
    }
}