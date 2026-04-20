package com.example.emailverificationauthenticationviaapi.data.login.repository

import com.example.emailverificationauthenticationviaapi.data.login.remote.api.LoginApi
import com.example.emailverificationauthenticationviaapi.data.login.remote.dto.LoginRequest
import com.example.emailverificationauthenticationviaapi.data.login.remote.dto.LoginResponse
import com.example.emailverificationauthenticationviaapi.domain.login.LoginRepository
import com.example.emailverificationauthenticationviaapi.domain.login.entity.LoginEntity
import com.example.emailverificationauthenticationviaapi.util.BaseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, LoginResponse>> {
        return flow {
            val response = loginApi.login(loginRequest)
            if (response.isSuccessful) {
                val body = response.body()!!
                val loginEntity = LoginEntity(body.status!!, body.message!!, body.accessToken!!, body.refreshToken!!)
                emit(BaseResult.Success(loginEntity))
            } else {
                val type = object : TypeToken<LoginResponse>() {}.type
                val err: LoginResponse = Gson().fromJson(response.errorBody()!!.charStream(), type)
                //err.code = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }
}