package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.modeldata.Sesi
import com.example.kiranapilates.repositori.AuthRepository
import com.example.kiranapilates.repositori.SesiRepository
import kotlinx.coroutines.launch

class SesiViewModel(
    private val sesiRepository: SesiRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    var daftarSesi by mutableStateOf<List<Sesi>>(emptyList())

    init { loadSesi() }

    fun loadSesi() {
        viewModelScope.launch {
            val response = sesiRepository.getSesi()
            daftarSesi = response.data ?: emptyList()
        }
    }

    fun updateSesi(token: String, id: Int, nama: String, jam: String, inst: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val response = sesiRepository.updateSesi(token, id, nama, jam, inst)
            if (response.status == "success") {
                loadSesi()
                onSuccess()
            }
        }
    }
}