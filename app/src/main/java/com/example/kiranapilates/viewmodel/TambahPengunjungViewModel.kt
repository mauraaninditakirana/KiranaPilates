package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.repositori.PengunjungRepository
import kotlinx.coroutines.launch

class TambahPengunjungViewModel(private val pengunjungRepository: PengunjungRepository) : ViewModel() {
    var namaInput by mutableStateOf("")
    var noHpInput by mutableStateOf("")
    var tipeInput by mutableStateOf("Guest") // Default Guest

    fun simpanPengunjung(onSuccess: () -> Unit, onError: (String) -> Unit) {

        if (namaInput.isEmpty()) {
            onError("Nama tidak boleh kosong")
            return
        }
        // isLetterOrDigit() mengecek apakah karakter itu Huruf atau Angka.
        // Tanda '!' berarti "JIKA BUKAN".
        if (!namaInput.first().isLetterOrDigit()) {
            onError("Nama tidak boleh diawali simbol/tanda baca")
            return
        }

        // 2. Cek No HP: Harus angka, minimal 10, maksimal 15
        if (noHpInput.length < 10 || noHpInput.length > 15) {
            onError("Nomor HP harus 10 - 15 digit")
            return
        }

        viewModelScope.launch {
            try {
                val response = pengunjungRepository.insertPengunjung(namaInput, noHpInput, tipeInput)
                if (response.status == "success") onSuccess() else onError(response.message ?: "Gagal")
            } catch (e: Exception) { onError("Kesalahan Jaringan") }
        }
    }
}