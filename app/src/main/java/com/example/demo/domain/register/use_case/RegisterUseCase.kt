package com.example.demo.domain.register.use_case

import com.example.demo.data.register.remote.request.RegisterRequest
import com.example.demo.data.register.remote.response.RegisterResponse
import com.example.demo.domain.register.RegisterRepository
import com.example.demo.domain.register.entity.RegisterEntity
import com.example.demo.util.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    suspend fun invoke(registerRequest: RegisterRequest) : Flow<BaseResult<RegisterEntity, RegisterResponse>> {

        return registerRepository.register(registerRequest)
    }
}