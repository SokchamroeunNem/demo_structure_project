package com.example.demo.data.register.repository

import com.example.demo.data.register.remote.api.RegisterApi
import com.example.demo.data.register.remote.request.RegisterRequest
import com.example.demo.data.register.remote.response.RegisterResponse
import com.example.demo.domain.register.RegisterRepository
import com.example.demo.domain.register.entity.RegisterEntity
import com.example.demo.util.BaseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val registerApi: RegisterApi): RegisterRepository {
    override suspend fun register(registerRequest: RegisterRequest): Flow<BaseResult<RegisterEntity, RegisterResponse>> {
        return flow {
            val response = registerApi.register(registerRequest)
            if (response.isSuccessful) {
                val body = response.body()!!
                val registerEntity = RegisterEntity(body.status!!, body.message!!, body.accessToken!!, body.refreshToken!!)
                emit(BaseResult.Success(registerEntity))
            } else {
                /*val type = object : TypeToken<RegisterResponse>(){}.type
                val err : RegisterResponse = Gson().fromJson(response.errorBody()!!.charStream(), type)
                err.code = response.code()
                emit(BaseResult.Error(err))*/
                val type = object : TypeToken<RegisterResponse>(){}.type
                val err : RegisterResponse = Gson().fromJson(response.errorBody()!!.charStream(), type)
                //err.code = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }
}