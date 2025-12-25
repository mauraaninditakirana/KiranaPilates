package com.example.kiranapilates.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CheckinViewModel : ViewModel() {

    var selectedDate by mutableStateOf("2025-12-26") // Sesuai tanggal hari ini
        private set

    fun updateDate(newDate: String) {
        selectedDate = newDate
    }
}