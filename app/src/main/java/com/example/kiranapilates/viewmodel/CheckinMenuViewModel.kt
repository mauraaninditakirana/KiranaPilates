package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kiranapilates.repositori.CheckinRepository
import java.text.SimpleDateFormat
import java.util.*

class CheckinMenuViewModel(private val checkinRepository: CheckinRepository) : ViewModel() {

    // State untuk menyimpan tanggal yang dipilih oleh Admin
    // Default diatur ke tanggal hari ini saat aplikasi dibuka
    var selectedDate by mutableStateOf(getCurrentDate())

    // Fungsi untuk memperbarui tanggal saat Admin menggunakan filter tanggal [cite: 244]
    fun onDateSelected(newDate: String) {
        selectedDate = newDate
    }

    // Helper untuk mendapatkan format tanggal standar (yyyy-MM-dd)
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}