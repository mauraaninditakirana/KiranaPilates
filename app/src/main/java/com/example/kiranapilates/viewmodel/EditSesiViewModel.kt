package com.example.kiranapilates.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.kiranapilates.repositori.SesiRepository

data class SesiUiState(
    val id_sesi: String = "",
    val jam_operasional: String = "",
    val nama_instruktur: String = ""
)

class EditSesiViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: SesiRepository
) : ViewModel() {
    private val idSesi: String = checkNotNull(savedStateHandle["id_sesi"])


    var editSesiUiState by mutableStateOf(SesiUiState())
        private set

    // Fungsi untuk update data state saat admin mengetik di form
    fun updateUiState(newSesiUiState: SesiUiState) {
        editSesiUiState = newSesiUiState
    }
}