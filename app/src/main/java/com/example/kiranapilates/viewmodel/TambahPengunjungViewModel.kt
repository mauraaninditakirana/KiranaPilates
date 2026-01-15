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

        // isLetterOrDigit() mengecek apakah karakter itu Huruf atau Angka.
        // Tanda '!' berarti "JIKA BUKAN".
        if (namaInput.isEmpty() || !namaInput.first().isLetterOrDigit()) {
            onError("Nama tidak boleh kosong atau diawali simbol")
            return
        }

        // 2. Cek No HP: Harus angka, minimal 10, maksimal 15
        if (noHpInput.length < 10 || noHpInput.length > 15) {
            onError("Nomor HP harus 10 - 15 digit")
            return
        }
        // 2. Cek Awalan (Wajib 08)
        if (!noHpInput.startsWith("08")) {
            onError("Nomor HP harus diawali '08'")
            return
        }

        // 3. Cek Pola Berulang (Anti 080000000, 089999999, 081111111)
        // Ambil angka setelah "08"
        val sisaAngka = noHpInput.substring(2)

        // Logika: toSet() akan mengelompokkan karakter unik.
        // Jika sisa angka "111111", maka uniknya cuma "1" (size = 1). Ini yang kita tolak.
        // Jika sisa angka "123456", maka uniknya ada 6 (size > 1). Ini lolos.
        if (sisaAngka.toSet().size == 1) {
            onError("Nomor HP tidak valid (Angka tidak boleh berulang semua)")
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