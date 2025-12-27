package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.repositori.PengunjungRepository
import kotlinx.coroutines.launch

class TambahPengunjungViewModel(private val pengunjungRepository: PengunjungRepository) : ViewModel() {
    var namaInput by mutableStateOf("")
    var noHpInput by mutableStateOf("")
    var tipeInput by mutableStateOf("Guest") // Default Guest

    fun simpanPengunjung(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = pengunjungRepository.insertPengunjung(namaInput, noHpInput, tipeInput)
                if (response.status == "success") onSuccess() else onError(response.message ?: "Gagal")
            } catch (e: Exception) { onError("Kesalahan Jaringan") }
        }
    }
}