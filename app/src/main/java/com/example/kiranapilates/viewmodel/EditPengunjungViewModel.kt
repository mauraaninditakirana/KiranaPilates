package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.modeldata.PengunjungUpdateBody
import com.example.kiranapilates.repositori.PengunjungRepository
import com.example.kiranapilates.uicontroller.route.DestinasiEditPengunjung
import kotlinx.coroutines.launch

class EditPengunjungViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengunjungRepository: PengunjungRepository
) : ViewModel() {

    // 1. Menangkap ID dari navigasi menggunakan SavedStateHandle
    private val pengunjungId: Int = checkNotNull(savedStateHandle[DestinasiEditPengunjung.PENGUNJUNG_ID_ARG])

    // 2. State untuk menampung input di form
    var namaInput by mutableStateOf("")
    var noHpInput by mutableStateOf("")
    var tipeInput by mutableStateOf("")
    var isTambahPaket by mutableStateOf(false)

    // 3. Pemicu otomatis untuk mengambil data lama saat halaman dibuka
    init {
        loadDataLama()
    }

    private fun loadDataLama() {
        viewModelScope.launch {
            try {
                // Mengambil data dari server lewat get_by_id.php
                val response = pengunjungRepository.getPengunjungById(pengunjungId)
                response.data?.firstOrNull()?.let {
                    // Mengisi form secara otomatis
                    namaInput = it.nama_lengkap
                    noHpInput = it.no_hp
                    tipeInput = it.tipe_pengunjung
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 4. Fungsi untuk mengirim perubahan data ke server dengan metode PUT
    // Di EditPengunjungViewModel.kt
    fun updatePengunjung(token: String, onSuccess: () -> Unit, onError: (String) -> Unit) {

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
                // Bungkus semua data ke dalam Map
                val dataUpdate = mapOf(
                    "id_pengunjung" to pengunjungId.toString(),
                    "nama_lengkap" to namaInput,
                    "no_hp" to noHpInput,
                    "tipe_pengunjung" to tipeInput,
                    "tambah_paket" to if (isTambahPaket) "1" else "0"
                )

                val response = pengunjungRepository.updatePengunjung(token, dataUpdate)

                if (response.status == "success") {
                    onSuccess() // Navigasi balik ke Detail jika berhasil
                }else {
                    // Jika server menolak, tampilkan errornya
                    onError(response.message ?: "Gagal Update")
                }
            } catch (e: Exception) {
                onError("Terjadi kesalahan jaringan")
                e.printStackTrace()
            }
        }
    }
}