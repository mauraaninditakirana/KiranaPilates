package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.repositori.SesiRepository
import com.example.kiranapilates.uicontroller.route.DestinasiSesiUpdate
import kotlinx.coroutines.launch

class SesiUpdateViewModel(
    savedStateHandle: SavedStateHandle, // Tambahan untuk menangkap ID
    private val sesiRepository: SesiRepository
) : ViewModel() {

    // 1. Ambil ID Sesi dari Navigasi (Bukan 0 lagi, tapi ID asli)
    private val idSesi: Int = checkNotNull(savedStateHandle[DestinasiSesiUpdate.SESI_ID_ARG])

    // State untuk menampung input edit
    var namaSesi by mutableStateOf("")
    var jamInput by mutableStateOf("")
    var instrukturInput by mutableStateOf("")

    // 2. INIT: Panggil data lama begitu ViewModel dibuat
    init {
        loadDataLama()
    }

    // Fungsi untuk mengambil data sesi dari database agar form terisi
    private fun loadDataLama() {
        viewModelScope.launch {
            try {
                // Pastikan kamu sudah menambahkan getSesiById di Repository & PHP
                val response = sesiRepository.getSesiById(idSesi)

                // Ambil data pertama dari list dan masukkan ke variabel state
                response.data?.firstOrNull()?.let {
                    namaSesi = it.nama_sesi
                    jamInput = it.jam_operasional
                    instrukturInput = it.nama_instruktur
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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
                e.printStackTrace()
            }
        }
    }
}