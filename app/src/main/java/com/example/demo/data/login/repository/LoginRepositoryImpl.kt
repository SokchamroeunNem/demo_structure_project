package com.example.demo.data.login.repository

import com.example.demo.data.login.remote.api.LoginApi
import com.example.demo.data.login.remote.request.LoginRequest
import com.example.demo.data.login.remote.response.LoginResponse
import com.example.demo.domain.login.LoginRepository
import com.example.demo.domain.login.entity.LoginEntity
import com.example.demo.util.BaseResult
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