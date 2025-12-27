package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.repositori.SesiRepository
import kotlinx.coroutines.launch

class SesiUpdateViewModel(private val sesiRepository: SesiRepository) : ViewModel() {

    // State untuk menampung input edit
    var idSesi by mutableStateOf(0)
    var namaSesi by mutableStateOf("")
    var jamInput by mutableStateOf("")
    var instrukturInput by mutableStateOf("")

    fun updateSesi(token: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Memanggil fungsi update di repository
                val response = sesiRepository.updateSesi(
                    token, idSesi, namaSesi, jamInput, instrukturInput
                )

                if (response.status == "success") {
                    onSuccess()
                }
            } catch (e: Exception) {
                // Handle error jaringan
            }
        }
    }
}