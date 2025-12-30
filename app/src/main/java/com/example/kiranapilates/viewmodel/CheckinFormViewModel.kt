package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.modeldata.Pengunjung
import com.example.kiranapilates.modeldata.Sesi
import com.example.kiranapilates.repositori.CheckinRepository
import com.example.kiranapilates.repositori.PengunjungRepository
import com.example.kiranapilates.repositori.SesiRepository
import kotlinx.coroutines.launch

class CheckinFormViewModel(
    private val checkinRepository: CheckinRepository,
    private val pengunjungRepository: PengunjungRepository,
    private val sesiRepository: SesiRepository
) : ViewModel() {

    // --- DATA MASTER ---
    var listPengunjung by mutableStateOf<List<Pengunjung>>(emptyList())
    var listSesi by mutableStateOf<List<Sesi>>(emptyList())

    // --- STATE UNTUK DROPDOWN PENCARIAN (YANG BARU DITAMBAHKAN) ---
    var searchQuery by mutableStateOf("") // Apa yang diketik user
    var isDropdownExpanded by mutableStateOf(false) // Apakah dropdown terbuka
    var selectedPengunjung by mutableStateOf<Pengunjung?>(null) // Objek pengunjung yang dipilih (untuk tampil detail)

    // --- LOGIKA FILTER: List yang berubah sesuai ketikan ---
    val filteredPengunjung: List<Pengunjung>
        get() {
            return if (searchQuery.isEmpty()) {
                listPengunjung // Tampilkan semua jika belum mengetik
            } else {
                listPengunjung.filter {
                    it.nama_lengkap.contains(searchQuery, ignoreCase = true)
                }
            }
        }

    // --- STATE ID UNTUK DIKIRIM KE API ---
    var selectedPengunjungId by mutableStateOf<Int?>(null)
    var selectedSesiId by mutableStateOf<Int?>(null)

    var checkinResult by mutableStateOf("") // Pesan pop-up

    init { loadData() }

    private fun loadData() {
        viewModelScope.launch {
            try {
                // Ambil Data Pengunjung
                val resPengunjung = pengunjungRepository.getAllPengunjung()
                listPengunjung = resPengunjung.data ?: emptyList()

                // Ambil Data Sesi (Pastikan nama fungsinya sesuai repo kamu, misal getSesi atau getAllSesi)
                // Di kode lamamu tertulis getSesi(), sesuaikan jika merah
                val resSesi = sesiRepository.getAllSesi()
                listSesi = resSesi.data ?: emptyList() // Gunakan safe call ?.
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // --- FUNGSI SAAT NAMA DIKLIK DI DROPDOWN ---
    fun selectPengunjung(p: Pengunjung) {
        selectedPengunjung = p
        selectedPengunjungId = p.id_pengunjung
        searchQuery = p.nama_lengkap // Teks input jadi nama lengkap
        isDropdownExpanded = false   // Tutup dropdown
    }

    fun submitCheckin(onSuccess: () -> Unit, onQuotaEmpty: (Int) -> Unit) {
        val pId = selectedPengunjungId ?: return
        val sId = selectedSesiId ?: return

        viewModelScope.launch {
            try {
                val response = checkinRepository.createCheckin(pId, sId)
                if (response.status == "success") {
                    checkinResult = "Check-in Berhasil"
                    onSuccess()
                } else if (response.message?.contains("Kuota", ignoreCase = true) == true) {
                    checkinResult = "Check-in gagal, kuota member habis"
                    onQuotaEmpty(pId) // Arahkan ke edit pengunjung untuk tambah paket
                } else {
                    checkinResult = response.message ?: "Gagal Check-in"
                }
            } catch (e: Exception) {
                checkinResult = "Terjadi kesalahan jaringan"
                e.printStackTrace()
            }
        }
    }
}