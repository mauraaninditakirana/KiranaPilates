package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.kiranapilates.repositori.SesiRepository

class EditSesiViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: SesiRepository
) : ViewModel() {
    // ID Sesi diambil dari navigasi
    private val idSesi: String = checkNotNull(savedStateHandle["id_sesi"])

    // State untuk form edit (jam & instruktur)
    var editSesiUiState by mutableStateOf(SesiUiState())
        private set
}