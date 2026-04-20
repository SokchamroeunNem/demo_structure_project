package com.example.emailverificationauthenticationviaapi.domain.register.use_case

import com.example.emailverificationauthenticationviaapi.data.register.remote.dto.RegisterRequest
import com.example.emailverificationauthenticationviaapi.data.register.remote.dto.RegisterResponse
import com.example.emailverificationauthenticationviaapi.domain.register.RegisterRepository
import com.example.emailverificationauthenticationviaapi.domain.register.entity.RegisterEntity
import com.example.emailverificationauthenticationviaapi.util.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    suspend fun invoke(registerRequest: RegisterRequest) : Flow<BaseResult<RegisterEntity, RegisterResponse>> {

        return registerRepository.register(registerRequest)
    }
}