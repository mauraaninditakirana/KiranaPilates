package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.modeldata.Pengunjung
import com.example.kiranapilates.repositori.PengunjungRepository
import kotlinx.coroutines.launch

sealed class DaftarUiState {
    data class Success(val pengunjung: List<Pengunjung>) : DaftarUiState()
    object Error : DaftarUiState()
    object Loading : DaftarUiState()
}

class DaftarPengunjungViewModel(private val repository: PengunjungRepository) : ViewModel() {
    // State untuk UI (Loading, Success, Error)
    var daftarUiState: DaftarUiState by mutableStateOf(DaftarUiState.Loading)
        private set

    // State untuk fitur Search (Wajib ada agar UI tidak merah)
    var searchQuery by mutableStateOf("")
        private set

    // Fungsi untuk update teks search dari UI
    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun getListPengunjung(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPengunjung(token)
                daftarUiState = DaftarUiState.Success(response.data)
            } catch (e: Exception) {
                daftarUiState = DaftarUiState.Error
            }
        }
    }
}