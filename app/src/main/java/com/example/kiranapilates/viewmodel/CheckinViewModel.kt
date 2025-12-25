package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kiranapilates.repositori.CheckinRepository
import com.example.kiranapilates.repositori.PengunjungRepository

class CheckinViewModel(
    private val checkinRepository: CheckinRepository,
    private val pengunjungRepository: PengunjungRepository
) : ViewModel() {

    // --- LOGIKA HALAMAN 9 ---
    var selectedDate by mutableStateOf("2025-12-26") // State filter tanggal

    // --- LOGIKA HALAMAN 10 ---
    var namaSearch by mutableStateOf("") // Untuk search di dropdown
    var idPengunjungTerpilih by mutableStateOf("")
    var sesiTerpilih by mutableStateOf("") // Radio button: Pagi/Sore/Malam

    fun updateDate(newDate: String) { selectedDate = newDate }


}