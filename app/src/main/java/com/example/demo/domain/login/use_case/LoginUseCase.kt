package com.example.demo.domain.login.use_case

import com.example.demo.data.login.remote.request.LoginRequest
import com.example.demo.data.login.remote.response.LoginResponse
import com.example.demo.domain.login.LoginRepository
import com.example.demo.domain.login.entity.LoginEntity
import com.example.demo.util.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun execute(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity, LoginResponse>> {
        return loginRepository.login(loginRequest)
    }
}