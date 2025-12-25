package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.repositori.PengunjungRepository
import kotlinx.coroutines.launch

class TambahPengunjungViewModel(private val repository: PengunjungRepository) : ViewModel() {

    var nama_lengkap by mutableStateOf("")
        private set
    var nohp by mutableStateOf("")
        private set
    var tipe_pengunjung by mutableStateOf("Reguler")
        private set

    fun updateNama(it: String) { nama_lengkap = it }
    fun updateNoHp(it: String) { nohp = it }

    fun simpanPengunjung() {
        viewModelScope.launch {
            try {
                // repository.insertPengunjung(...)
            } catch (e: Exception) { }
        }
    }
}