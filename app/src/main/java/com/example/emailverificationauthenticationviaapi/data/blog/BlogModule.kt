package com.example.emailverificationauthenticationviaapi.data.blog

import com.example.emailverificationauthenticationviaapi.data.blog.remote.api.BlogApi
import com.example.emailverificationauthenticationviaapi.data.blog.repository.BlogRepositoryImpl
import com.example.emailverificationauthenticationviaapi.data.common.module.NetworkModule
import com.example.emailverificationauthenticationviaapi.domain.blog.BlogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class BlogModule {

    @Singleton
    @Provides
    fun provideBlogApi(retrofit: Retrofit): BlogApi {
        return retrofit.create(BlogApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBlogRepository(productApi: BlogApi): BlogRepository {
        return BlogRepositoryImpl(productApi)
    }

}