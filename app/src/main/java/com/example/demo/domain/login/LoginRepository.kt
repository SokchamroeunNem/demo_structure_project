package com.example.demo.domain.login

import com.example.demo.data.login.remote.request.LoginRequest
import com.example.demo.data.login.remote.response.LoginResponse
import com.example.demo.domain.login.entity.LoginEntity
import com.example.demo.util.BaseResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, LoginResponse>>
}