package com.example.kiranapilates.viewmodel


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.kiranapilates.repositori.PengunjungRepository

class DetailPengunjungViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PengunjungRepository
) : ViewModel() {
    // ambil ID yang dikirim lewat rute navigasi
    private val idPengunjung: String = checkNotNull(savedStateHandle["id_pengunjung"])


}