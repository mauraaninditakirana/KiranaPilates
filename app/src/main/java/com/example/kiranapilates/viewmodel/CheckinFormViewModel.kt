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

    var listPengunjung by mutableStateOf<List<Pengunjung>>(emptyList())
    var listSesi by mutableStateOf<List<Sesi>>(emptyList())

    var selectedPengunjungId by mutableStateOf<Int?>(null)
    var selectedSesiId by mutableStateOf<Int?>(null)

    var checkinResult by mutableStateOf("") // Untuk pop-up pesan berhasil/gagal

    init { loadData() }

    private fun loadData() {
        viewModelScope.launch {
            listPengunjung = pengunjungRepository.getAllPengunjung().data ?: emptyList()
            listSesi = sesiRepository.getSesi().data ?: emptyList()
        }
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
                } else if (response.message.contains("Kuota", ignoreCase = true)) {
                    checkinResult = "Check-in gagal, kuota member habis"
                    onQuotaEmpty(pId) // Mengarahkan ke edit pengunjung
                }
            } catch (e: Exception) {
                checkinResult = "Terjadi kesalahan jaringan"
            }
        }
    }
}