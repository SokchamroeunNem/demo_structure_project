package com.example.demo.domain.register


import com.example.demo.data.register.remote.request.RegisterRequest
import com.example.demo.data.register.remote.response.RegisterResponse
import com.example.demo.domain.register.entity.RegisterEntity
import com.example.demo.util.BaseResult
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(registerRequest: RegisterRequest) : Flow<BaseResult<RegisterEntity, RegisterResponse>>
}