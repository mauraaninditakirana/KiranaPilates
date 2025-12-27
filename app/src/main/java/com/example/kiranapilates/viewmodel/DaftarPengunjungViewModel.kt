package com.example.kiranapilates.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiranapilates.modeldata.Pengunjung
import com.example.kiranapilates.repositori.PengunjungRepository
import kotlinx.coroutines.launch

class DaftarPengunjungViewModel(private val pengunjungRepository: PengunjungRepository) : ViewModel() {
    var daftarPengunjung by mutableStateOf<List<Pengunjung>>(emptyList())
    var filteredPengunjung by mutableStateOf<List<Pengunjung>>(emptyList())
    var searchQuery by mutableStateOf("")

    init { getPengunjung() }

    fun getPengunjung() {
        viewModelScope.launch {
            try {
                val response = pengunjungRepository.getAllPengunjung()
                daftarPengunjung = response.data ?: emptyList()
                filteredPengunjung = daftarPengunjung
            } catch (e: Exception) { /* Handle error */ }
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        filteredPengunjung = if (query.isEmpty()) {
            daftarPengunjung
        } else {
            daftarPengunjung.filter { it.nama_lengkap.contains(query, ignoreCase = true) }
        }
    }
}