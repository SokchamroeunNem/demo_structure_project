package com.example.emailverificationauthenticationviaapi.domain.login.use_case

import com.example.emailverificationauthenticationviaapi.data.login.remote.dto.LoginRequest
import com.example.emailverificationauthenticationviaapi.data.login.remote.dto.LoginResponse
import com.example.emailverificationauthenticationviaapi.domain.login.LoginRepository
import com.example.emailverificationauthenticationviaapi.domain.login.entity.LoginEntity
import com.example.emailverificationauthenticationviaapi.util.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun execute(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, LoginResponse>> {
        return loginRepository.login(loginRequest)
    }
}