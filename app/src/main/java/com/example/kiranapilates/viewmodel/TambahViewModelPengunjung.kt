package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kiranapilates.modeldata.Pengunjung
import com.example.kiranapilates.repositori.PengunjungRepository

data class TambahUiState(
    val nama_lengkap: String = "",
    val no_hp: String = "",
    val tipe_pengunjung: String = "",
    val isEntryValid: Boolean = false
)

class TambahPengunjungViewModel(private val repository: PengunjungRepository) : ViewModel() {
    var uiState by mutableStateOf(TambahUiState())
        private set

    fun updateUiState(nama: String, hp: String, tipe: String) {
        uiState = uiState.copy(
            nama_lengkap = nama,
            no_hp = hp,
            tipe_pengunjung = tipe,
            isEntryValid = nama.isNotBlank() && hp.isNotBlank()
        )
    }
}