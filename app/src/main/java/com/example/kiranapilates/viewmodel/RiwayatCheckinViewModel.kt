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

class RiwayatCheckinViewModel(private val checkinRepository: CheckinRepository) : ViewModel() {

    // Menyimpan seluruh data check-in dari server
    private var allRiwayat by mutableStateOf<List<Checkin>>(emptyList())

    // Data yang sudah difilter untuk ditampilkan di UI
    var filteredRiwayat by mutableStateOf<List<Checkin>>(emptyList())

    // State untuk filter
    var selectedDate by mutableStateOf(getCurrentDate()) // Default: Hari ini
    var selectedSesiId by mutableStateOf(0) // Diambil dari card yang diklik di Halaman 9

    init {
        loadAllRiwayat()
    }

    // Mengambil semua data riwayat dari backend
    fun loadAllRiwayat() {
        viewModelScope.launch {
            try {
                // Asumsi repository memiliki fungsi getSemuaCheckin
                // Jika belum, kita bisa gunakan filter di query SQL backend
                // Namun untuk kodingan ini kita ambil dan filter di Kotlin
                // val response = checkinRepository.getHistory()
                // allRiwayat = response.data ?: emptyList()
                filterData()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    // Logika Filter: Berdasarkan Tanggal DAN ID Sesi
    fun filterData() {
        filteredRiwayat = allRiwayat.filter { checkin ->
            val isSameDate = checkin.tanggal_checkin.startsWith(selectedDate)
            val isSameSesi = checkin.id_sesi == selectedSesiId
            isSameDate && isSameSesi
        }
    }

    // Fungsi pembantu untuk mendapatkan tanggal hari ini (yyyy-MM-dd)
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}