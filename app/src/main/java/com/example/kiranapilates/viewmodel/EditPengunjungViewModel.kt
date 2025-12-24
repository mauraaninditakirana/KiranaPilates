package com.example.kiranapilates.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.kiranapilates.repositori.PengunjungRepository

class EditPengunjungViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PengunjungRepository
) : ViewModel() {

    var editUiState by mutableStateOf(TambahUiState())
        private set

    // ID pengunjung yang mau diedit (dari navigasi)
    private val idPengunjung: String = checkNotNull(savedStateHandle["id_pengunjung"])
}