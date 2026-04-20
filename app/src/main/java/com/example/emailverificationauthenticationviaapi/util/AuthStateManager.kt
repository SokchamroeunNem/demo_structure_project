package com.example.emailverificationauthenticationviaapi.util

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/*class AuthStateManager @Inject constructor() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Valid)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun notifyAuthFailure() {
        _authState.value = AuthState.NeedLogin
    }

    enum class AuthState {
        Valid,
        NeedLogin
    }
}*/
@HiltViewModel
class AuthStateManager @Inject constructor() : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Valid)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun notifyAuthFailure() {
        _authState.value = AuthState.NeedLogin
    }

    enum class AuthState {
        Valid,
        NeedLogin
    }
}



