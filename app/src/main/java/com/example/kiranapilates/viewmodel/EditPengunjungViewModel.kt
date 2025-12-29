package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.repositori.PengunjungRepository
import com.example.kiranapilates.uicontroller.route.DestinasiEditPengunjung
import kotlinx.coroutines.launch

class EditPengunjungViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengunjungRepository: PengunjungRepository
) : ViewModel() {

    // Menangkap ID dari navigasi
    private val pengunjungId: Int = checkNotNull(savedStateHandle[DestinasiEditPengunjung.PENGUNJUNG_ID_ARG])

    var namaInput by mutableStateOf("")
    var noHpInput by mutableStateOf("")
    var tipeInput by mutableStateOf("")
    var isTambahPaket by mutableStateOf(false)

    init {
        loadDataLama()
    }

    private fun loadDataLama() {
        viewModelScope.launch {
            try {
                // Mengambil data dari get_by_id.php
                val response = pengunjungRepository.getPengunjungById(pengunjungId)
                response.data?.firstOrNull()?.let {
                    namaInput = it.nama_lengkap
                    noHpInput = it.no_hp
                    tipeInput = it.tipe_pengunjung
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePengunjung(token: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val tambahPaketVal = if (isTambahPaket) "1" else "0"
                val response = pengunjungRepository.updatePengunjung(
                    token, pengunjungId, namaInput, noHpInput, tipeInput, tambahPaketVal
                )
                // Pindah halaman jika server sukses mengupdate
                if (response.status == "success") {
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}