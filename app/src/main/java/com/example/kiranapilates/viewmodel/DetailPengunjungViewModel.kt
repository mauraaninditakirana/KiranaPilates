package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.modeldata.Pengunjung
import com.example.kiranapilates.repositori.PengunjungRepository
import kotlinx.coroutines.launch

class DetailPengunjungViewModel(private val pengunjungRepository: PengunjungRepository) : ViewModel() {
    var pengunjungDetail by mutableStateOf<Pengunjung?>(null)

    fun deletePengunjung(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = pengunjungRepository.deletePengunjung(id)
                if (response.status == "success") onSuccess()
            } catch (e: Exception) { /* Handle error */ }
        }
    }
}