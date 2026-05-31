package com.example.demo.data.common.utils

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.demo.data.login.remote.response.LoginResponse
import com.example.demo.data.refresh_token.remote.api.RefreshTokenApi
import com.example.demo.data.refresh_token.remote.request.TokenRequest
import com.example.demo.preferences.SharedPrefs
import com.example.demo.util.AuthStateManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthAuthenticator(
    private val tokenManager: SharedPrefs,
    private val authStateManager: AuthStateManager // Use AuthStateManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        val refreshToken = runBlocking {
            tokenManager.getRefreshToken().first()
        }

        return runBlocking {

            val newToken = getNewToken(refreshToken)

            if (!newToken.isSuccessful || newToken.body() == null) {
                Log.d("AuthAuthenticator", "authenticate: ${newToken.code()}")
                //Couldn't refresh the token, so restart the login process
                //tokenManager.deleteToken()
                //tokenManager.deleteRefreshToken()
                // Navigate to the login screen
                runOnUiThread {
                    authStateManager.notifyAuthFailure() // Notify state manager
                }
            }

            newToken.body()?.let {
                //tokenManager.saveToken(it.accessToken!!)
                //tokenManager.saveRefreshToken(it.refreshToken!!)
                response.request.newBuilder().header("Authorization", "Bearer ${it.accessToken}")
                    .build()
            }
        }
    }

    // Helper function to ensure code runs on the main thread
    private fun runOnUiThread(action: () -> Unit) {
        Handler(Looper.getMainLooper()).post {
            action()
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<LoginResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
        val service = retrofit.create(RefreshTokenApi::class.java)

        return service.refreshToken(TokenRequest(refreshToken!!))
    }

}
