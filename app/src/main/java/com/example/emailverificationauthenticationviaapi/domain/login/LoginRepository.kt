package com.example.emailverificationauthenticationviaapi.domain.login

import com.example.emailverificationauthenticationviaapi.data.login.remote.dto.LoginRequest
import com.example.emailverificationauthenticationviaapi.data.login.remote.dto.LoginResponse
import com.example.emailverificationauthenticationviaapi.domain.login.entity.LoginEntity
import com.example.emailverificationauthenticationviaapi.util.BaseResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, LoginResponse>>
}