package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.modeldata.Sesi
import com.example.kiranapilates.repositori.SesiRepository
import kotlinx.coroutines.launch

class SesiViewModel(private val repository: SesiRepository) : ViewModel() {
    var sesiList by mutableStateOf<List<Sesi>>(listOf())
        private set

    // Fungsi untuk Halaman 7 (Ambil 3 Card Sesi)
    fun fetchSesi(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSesi(token)
                sesiList = response.data
            } catch (e: Exception) { }
        }
    }
}