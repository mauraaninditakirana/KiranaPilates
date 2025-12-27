package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.repositori.PengunjungRepository
import kotlinx.coroutines.launch

class EditPengunjungViewModel(private val pengunjungRepository: PengunjungRepository) : ViewModel() {
    var idPengunjung by mutableStateOf(0)
    var namaInput by mutableStateOf("")
    var noHpInput by mutableStateOf("")
    var tipeInput by mutableStateOf("")
    var isTambahPaket by mutableStateOf(false)

    fun updatePengunjung(token: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val tambahPaketVal = if (isTambahPaket) "1" else "0"
                val response = pengunjungRepository.updatePengunjung(
                    token, idPengunjung, namaInput, noHpInput, tipeInput, tambahPaketVal
                )
                if (response.status == "success") onSuccess()
            } catch (e: Exception) { /* Handle error */ }
        }
    }
}