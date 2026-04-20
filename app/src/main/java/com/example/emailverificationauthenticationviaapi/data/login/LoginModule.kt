package com.example.emailverificationauthenticationviaapi.data.login

import com.example.emailverificationauthenticationviaapi.data.common.module.NetworkModule
import com.example.emailverificationauthenticationviaapi.data.login.remote.api.LoginApi
import com.example.emailverificationauthenticationviaapi.data.login.repository.LoginRepositoryImpl
import com.example.emailverificationauthenticationviaapi.domain.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginApi: LoginApi): LoginRepository {
        return LoginRepositoryImpl(loginApi)
    }

}