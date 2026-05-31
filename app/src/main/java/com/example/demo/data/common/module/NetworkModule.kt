package com.example.demo.data.common.module

import com.example.demo.BuildConfig
import com.example.demo.data.common.utils.AuthAuthenticator
import com.example.demo.data.common.utils.RequestInterceptor
import com.example.demo.preferences.SharedPrefs
import com.example.demo.util.AuthStateManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl(BuildConfig.ENVIRONMENT_NAME)
        }.build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        requestInterceptor: RequestInterceptor,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(requestInterceptor)
            authenticator(authAuthenticator)
            //Ensures your app only trusts the bank's valid SSL certificate.
            //connectionSpecs(listOf(ConnectionSpec.MODERN_TLS)) TLS (Transport Layer Security) is a cryptographic protocol that ensures secure communication over the internet.
        }.build()

        // SSL Pinning
        /*val certificatePinner = CertificatePinner.Builder()
            .add("api.bank.com", "sha256/XYZ123...") // Replace with actual cert fingerprint
            .build()

        val client = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .build()*/
    }

    @Provides
    fun provideRequestInterceptor(prefs: SharedPrefs): RequestInterceptor {
        return RequestInterceptor(prefs)
    }

    /*@Singleton
    @Provides
    fun provideAuthAuthenticator(
        prefs: SharedPrefs
    ): AuthAuthenticator = AuthAuthenticator(prefs)*/
    @Provides
    @Singleton
    fun provideAuthAuthenticator(
        prefs: SharedPrefs,
        authStateManager: AuthStateManager
    ): AuthAuthenticator = AuthAuthenticator(prefs, authStateManager)

    @Provides
    @Singleton
    fun provideAuthStateManager(): AuthStateManager = AuthStateManager()

}