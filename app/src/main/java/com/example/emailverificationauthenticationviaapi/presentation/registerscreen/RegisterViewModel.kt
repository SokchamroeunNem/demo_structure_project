package com.example.emailverificationauthenticationviaapi.presentation.registerscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emailverificationauthenticationviaapi.data.register.remote.dto.RegisterRequest
import com.example.emailverificationauthenticationviaapi.data.register.remote.dto.RegisterResponse
import com.example.emailverificationauthenticationviaapi.domain.register.entity.RegisterEntity
import com.example.emailverificationauthenticationviaapi.domain.register.use_case.RegisterUseCase
import com.example.emailverificationauthenticationviaapi.preferences.SharedPrefs
import com.example.emailverificationauthenticationviaapi.util.BaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase, private val sharedPrefs: SharedPrefs
) : ViewModel() {

    /*
    private val state = MutableStateFlow<RegisterActivityState>(RegisterActivityState.Init)
    val mState: StateFlow<RegisterActivityState> get() = state
    */

    var stateActivity by mutableStateOf(RegisterActivityStateData())
        private set

    var registrationState by mutableStateOf(RegistrationFormState())
        private set

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {

            is RegistrationFormEvent.NameChanged -> {
                val error = validateName(event.name)
                registrationState = registrationState.copy(name = event.name, nameError = error)
            }

            is RegistrationFormEvent.EmailChanged -> {
                val error = validateEmail(event.email)
                registrationState = registrationState.copy(email = event.email, emailError = error)
            }

            is RegistrationFormEvent.PasswordChanged -> {
                val error = validatePassword(event.password)
                registrationState =
                    registrationState.copy(password = event.password, passwordError = error)
            }

            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                val error =
                    validateRepeatedPassword(registrationState.password, event.repeatedPassword)
                registrationState = registrationState.copy(
                    repeatedPassword = event.repeatedPassword, repeatedPasswordError = error
                )
            }

            is RegistrationFormEvent.AcceptTerms -> {
                val error = validateTerms(event.isAccepted)
                registrationState =
                    registrationState.copy(acceptedTerms = event.isAccepted, termsError = error)
            }

            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val nameError = validateName(registrationState.name)
        val emailError = validateEmail(registrationState.email)
        val passwordError = validatePassword(registrationState.password)
        val repeatedPasswordError =
            validateRepeatedPassword(registrationState.password, registrationState.repeatedPassword)

        registrationState = registrationState.copy(
            nameError = nameError,
            emailError = emailError,
            passwordError = passwordError,
            repeatedPasswordError = repeatedPasswordError
        )

        if (emailError == null && passwordError == null && repeatedPasswordError == null) {
            // All validations passed, proceed with form submission
            // You can add your form submission logic here
            register(
                RegisterRequest(
                    registrationState.email,
                    registrationState.name,
                    registrationState.password,
                    registrationState.repeatedPassword
                )
            )

            // Clear form data after successful submission
            clearFormData()
        }

    }

    private fun clearFormData() {
        registrationState = RegistrationFormState()
    }

    fun validateName(name: String): String? {
        return if (name.isBlank()) "Name cannot be empty" else null
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

    fun validateRepeatedPassword(password: String, repeatedPassword: String): String? {
        return if (password != repeatedPassword) "Passwords do not match" else null
    }

    fun validateTerms(acceptedTerms: Boolean): String? {
        return if (!acceptedTerms) "You must accept the terms" else null
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

    private fun successRegister(registerEntity: RegisterEntity) {
        stateActivity = stateActivity.copy(registerEntity = registerEntity)
    }

    private fun errorRegister(rawResponse: RegisterResponse) {
        stateActivity = stateActivity.copy(rawResponse = rawResponse)
    }

    private fun setSuccess() {
        stateActivity = stateActivity.copy(isSuccess = true)
    }

    private fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            registerUseCase.invoke(registerRequest).onStart {
                setLoading()
                //delay(3000)
            }.catch { exception ->
                showToast(exception.message.toString())
                hideLoading()
            }.collect { result ->
                hideLoading()
                when (result) {
                    is BaseResult.Success -> {
                        sharedPrefs.saveToken(result.data.accessToken)
                        sharedPrefs.saveRefreshToken(result.data.refreshToken)
                        successRegister(result.data)
                        setSuccess()
                    }

                    is BaseResult.Error -> {
                        errorRegister(result.rawResponse)
                    }
                }
            }
        }
    }

}

data class RegistrationFormState(
    var name: String = "",
    val nameError: String? = null,
    var email: String = "",
    val emailError: String? = null,
    var password: String = "",
    val passwordError: String? = null,
    var repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
    val acceptedTerms: Boolean = false,
    val termsError: String? = null
)

sealed class RegistrationFormEvent {
    data class NameChanged(val name: String) : RegistrationFormEvent()
    data class EmailChanged(val email: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()
    data class RepeatedPasswordChanged(
        val repeatedPassword: String
    ) : RegistrationFormEvent()

    data class AcceptTerms(val isAccepted: Boolean) : RegistrationFormEvent()

    object Submit : RegistrationFormEvent()
}


data class RegisterActivityStateData(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var message: String? = null,
    var registerEntity: RegisterEntity? = null,
    var rawResponse: RegisterResponse? = null
)

sealed class RegisterActivityState {
    object Init : RegisterActivityState()
    data class IsLoading(val isLoading: Boolean) : RegisterActivityState()
    data class ShowToast(val message: String) : RegisterActivityState()
    data class SuccessRegister(val registerEntity: RegisterEntity) : RegisterActivityState()
    data class ErrorRegister(val rawResponse: RegisterResponse) : RegisterActivityState()
}


