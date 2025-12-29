package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.modeldata.PengunjungUpdateBody
import com.example.kiranapilates.repositori.PengunjungRepository
import com.example.kiranapilates.uicontroller.route.DestinasiEditPengunjung
import kotlinx.coroutines.launch

class EditPengunjungViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengunjungRepository: PengunjungRepository
) : ViewModel() {

    // 1. Menangkap ID dari navigasi menggunakan SavedStateHandle
    private val pengunjungId: Int = checkNotNull(savedStateHandle[DestinasiEditPengunjung.PENGUNJUNG_ID_ARG])

    // 2. State untuk menampung input di form
    var namaInput by mutableStateOf("")
    var noHpInput by mutableStateOf("")
    var tipeInput by mutableStateOf("")
    var isTambahPaket by mutableStateOf(false)

    // 3. Pemicu otomatis untuk mengambil data lama saat halaman dibuka
    init {
        loadDataLama()
    }

    private fun loadDataLama() {
        viewModelScope.launch {
            try {
                // Mengambil data dari server lewat get_by_id.php
                val response = pengunjungRepository.getPengunjungById(pengunjungId)
                response.data?.firstOrNull()?.let {
                    // Mengisi form secara otomatis
                    namaInput = it.nama_lengkap
                    noHpInput = it.no_hp
                    tipeInput = it.tipe_pengunjung
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 4. Fungsi untuk mengirim perubahan data ke server dengan metode PUT
    // Di EditPengunjungViewModel.kt
    fun updatePengunjung(token: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Bungkus semua data ke dalam Map
                val dataUpdate = mapOf(
                    "id_pengunjung" to pengunjungId.toString(),
                    "nama_lengkap" to namaInput,
                    "no_hp" to noHpInput,
                    "tipe_pengunjung" to tipeInput,
                    "tambah_paket" to if (isTambahPaket) "1" else "0"
                )

                val response = pengunjungRepository.updatePengunjung(token, dataUpdate)

                if (response.status == "success") {
                    onSuccess() // Navigasi balik ke Detail jika berhasil
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}