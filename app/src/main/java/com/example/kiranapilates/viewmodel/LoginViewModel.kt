package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.repositori.AuthRepository
import kotlinx.coroutines.launch

// State untuk memantau status Login di UI
sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    data class Success(val token: String) : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    // Menyimpan input dari textfield
    var usernameInput by mutableStateOf("")
    var passwordInput by mutableStateOf("")

    // Status proses login
    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Idle)
        private set

    fun login() {
        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            loginUiState = LoginUiState.Error("Username dan Password wajib diisi")
            return
        }

        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            try {
                val response = authRepository.login(usernameInput, passwordInput)
                if (response.status == "success" && response.data != null) {
                    loginUiState = LoginUiState.Success(response.data.token)
                } else {
                    loginUiState = LoginUiState.Error(response.message ?: "Login Gagal")
                }
            } catch (e: Exception) {
                loginUiState = LoginUiState.Error("Terjadi kesalahan jaringan: ${e.message}")
            }
        }
    }

    fun resetState() {
        loginUiState = LoginUiState.Idle
    }
}