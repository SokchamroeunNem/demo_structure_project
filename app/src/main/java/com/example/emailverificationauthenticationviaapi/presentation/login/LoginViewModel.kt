package com.example.emailverificationauthenticationviaapi.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emailverificationauthenticationviaapi.data.login.remote.dto.LoginRequest
import com.example.emailverificationauthenticationviaapi.data.login.remote.dto.LoginResponse
import com.example.emailverificationauthenticationviaapi.domain.login.entity.LoginEntity
import com.example.emailverificationauthenticationviaapi.domain.login.use_case.LoginUseCase
import com.example.emailverificationauthenticationviaapi.preferences.SharedPrefs
import com.example.emailverificationauthenticationviaapi.util.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase, private val sharedPrefs: SharedPrefs
) : ViewModel() {

    var stateActivity by mutableStateOf(LoginActivityStateData())
        private set

    var loginState by mutableStateOf(LoginFormState())
        private set

    fun onEvent(event: LoginFormEvent) {
        when (event) {

            is LoginFormEvent.EmailChanged -> {
                val error = validateEmail(event.email)
                loginState = loginState.copy(email = event.email, emailError = error)
            }

            is LoginFormEvent.PasswordChanged -> {
                val error = validatePassword(event.password)
                loginState = loginState.copy(password = event.password, passwordError = error)
            }

            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailError = validateEmail(loginState.email)
        val passwordError = validatePassword(loginState.password)

        loginState = loginState.copy(
            emailError = emailError, passwordError = passwordError
        )

        if (emailError == null && passwordError == null) {
            // All validations passed, proceed with form submission
            // You can add your form submission logic here
            Log.d("zzxxcc", "email: ${loginState.email} and password: ${loginState.password}")
            login(
                LoginRequest(
                    loginState.email, loginState.password
                )
            )

            // Clear form data after successful submission
            clearFormData()
        }
    }

    private fun clearFormData() {
        loginState = LoginFormState()
    }

    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email cannot be empty"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email address"
            else -> null
        }
    }

    fun validatePassword(password: String): String? {
        return if (password.length < 6) "Password must be at least 8 characters long" else null
    }

    /* Activity State */
    private fun setLoading() {
        stateActivity = stateActivity.copy(isLoading = true)
    }

    private fun hideLoading() {
        stateActivity = stateActivity.copy(isLoading = false)
    }

    private fun showToast(message: String) {
        stateActivity = stateActivity.copy(message = message)
    }

    private fun successLogin(loginEntity: LoginEntity) {
        stateActivity = stateActivity.copy(loginEntity = loginEntity)
    }

    private fun errorLogin(rawResponse: LoginResponse) {
        stateActivity = stateActivity.copy(rawResponse = rawResponse)
    }

    private fun setSuccess() {
        stateActivity = stateActivity.copy(isSuccess = true)
    }

    private fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase.execute(loginRequest).onStart {
                setLoading()
            }.catch { exception ->
                hideLoading()
                showToast(exception.message.toString())
                Log.d("zzxxcc", "catch catch: ${exception.message.toString()}")
            }.collect { result ->
                hideLoading()
                when (result) {
                    is BaseResult.Error -> {
                        errorLogin(result.rawResponse)
                        Log.d("zzxxcc", "errorLogin errorLogin: ${result.rawResponse}")
                    }

                    is BaseResult.Success -> {
                        sharedPrefs.saveToken(result.data.accessToken)
                        sharedPrefs.saveRefreshToken(result.data.refreshToken)
                        Log.d("zzxxcc", "xxxx accessToken: ${result.data.accessToken}")
                        Log.d("zzxxcc", "xxxx refreshToken: ${result.data.refreshToken}")
                        setSuccess()
                        successLogin(result.data)
                    }
                }
            }
        }
    }

}

data class LoginFormState(
    var email: String = "",
    val emailError: String? = null,
    var password: String = "",
    val passwordError: String? = null,
)

sealed class LoginFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    object Submit : LoginFormEvent()
}

data class LoginActivityStateData(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var message: String? = null,
    var loginEntity: LoginEntity? = null,
    var rawResponse: LoginResponse? = null
)

sealed class LoginActivityState {
    object Init : LoginActivityState()
    data class IsLoading(val isLoading: Boolean) : LoginActivityState()
    data class ShowToast(val message: String) : LoginActivityState()
    data class SuccessLogin(val loginEntity: LoginEntity) : LoginActivityState()
    data class ErrorLogin(val rawResponse: LoginResponse) : LoginActivityState()
}


