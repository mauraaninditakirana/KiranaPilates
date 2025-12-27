package com.example.kiranapilates.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.repositori.AuthRepository
import kotlinx.coroutines.launch

class DashboardViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun logout(token: String, onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                authRepository.logout(token)
                onLogoutSuccess()
            } catch (e: Exception) {
                // Tetap logout di sisi client jika network error
                onLogoutSuccess()
            }
        }
    }
}