package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.modeldata.Checkin
import com.example.kiranapilates.repositori.CheckinRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RiwayatCheckinViewModel(
    private val checkinRepository: CheckinRepository
) : ViewModel() {

    // Data riwayat untuk ditampilkan di UI
    var riwayatList by mutableStateOf<List<Checkin>>(emptyList())
        private set

    // State untuk loading dan error
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // State untuk filter
    var selectedSesiId by mutableStateOf(0)
        private set

    var selectedDate by mutableStateOf(getCurrentDate())
        private set

    // Fungsi untuk load data dari backend
    fun loadRiwayatCheckin() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val response = checkinRepository.getRiwayatCheckin(
                    idSesi = selectedSesiId,
                    tanggal = selectedDate
                )

                if (response.status == "success") {
                    riwayatList = response.data ?: emptyList()
                } else {
                    errorMessage = response.message
                    riwayatList = emptyList()
                }
            } catch (e: Exception) {
                errorMessage = "Gagal memuat data: ${e.message}"
                riwayatList = emptyList()
            } finally {
                isLoading = false
            }
        }
    }

    // Fungsi untuk update filter dan reload data
    fun updateFilter(sesiId: Int, tanggal: String) {
        selectedSesiId = sesiId
        selectedDate = tanggal
        loadRiwayatCheckin()
    }

    // Fungsi pembantu untuk mendapatkan tanggal hari ini (yyyy-MM-dd)
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}