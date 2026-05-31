package com.example.demo.data.post

import com.example.demo.data.common.module.NetworkModule
import com.example.demo.data.post.remote.api.PostApi
import com.example.demo.data.post.repository.PostRepositoryImpl
import com.example.demo.domain.post.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class PostModule {

    @Singleton
    @Provides
    fun providePostApi(retrofit: Retrofit): PostApi {
        return retrofit.create(PostApi::class.java)
    }

    @Singleton
    @Provides
    fun providePostRepository(productApi: PostApi): PostRepository {
        return PostRepositoryImpl(productApi)
    }

}