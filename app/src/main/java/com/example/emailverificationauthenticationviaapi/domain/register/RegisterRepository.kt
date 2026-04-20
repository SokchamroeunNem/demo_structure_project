package com.example.emailverificationauthenticationviaapi.domain.register


import com.example.emailverificationauthenticationviaapi.data.register.remote.dto.RegisterRequest
import com.example.emailverificationauthenticationviaapi.data.register.remote.dto.RegisterResponse
import com.example.emailverificationauthenticationviaapi.domain.register.entity.RegisterEntity
import com.example.emailverificationauthenticationviaapi.util.BaseResult
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(registerRequest: RegisterRequest) : Flow<BaseResult<RegisterEntity, RegisterResponse>>
}