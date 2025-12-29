package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.modeldata.Pengunjung
import com.example.kiranapilates.repositori.PengunjungRepository
import com.example.kiranapilates.uicontroller.route.DestinasiDetailPengunjung // Pastikan import destinasi kamu benar
import kotlinx.coroutines.launch

class DetailPengunjungViewModel(
    savedStateHandle: SavedStateHandle, // TAMBAHKAN INI: Untuk ambil ID dari navigasi
    private val pengunjungRepository: PengunjungRepository
) : ViewModel() {

    // Ambil ID yang dikirim dari halaman daftar (pastikan nama argumennya sesuai dengan PetaNavigasi)
    private val pengunjungId: Int = checkNotNull(savedStateHandle[DestinasiDetailPengunjung.PENGUNJUNG_ID_ARG])

    var pengunjungDetail by mutableStateOf<Pengunjung?>(null)
    var isLoading by mutableStateOf(false)
    var isError by mutableStateOf(false)

    // Pemicu otomatis saat halaman dibuka
    init {
        getDetailPengunjung()
    }

    fun getDetailPengunjung() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = pengunjungRepository.getPengunjungById(pengunjungId)
                // Sesuaikan: Jika response.data adalah List, ambil yang pertama (.firstOrNull)
                // Jika response.data langsung object Pengunjung, langsung masukkan
                pengunjungDetail = response.data?.firstOrNull()
                isError = pengunjungDetail == null
            } catch (e: Exception) {
                isError = true
            } finally {
                isLoading = false
            }
        }
    }

    fun deletePengunjung(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = pengunjungRepository.deletePengunjung(pengunjungId)
                if (response.status == "success") {
                    onSuccess()
                }
            } catch (e: Exception) {
                // Handle error hapus
            }
        }
    }
}