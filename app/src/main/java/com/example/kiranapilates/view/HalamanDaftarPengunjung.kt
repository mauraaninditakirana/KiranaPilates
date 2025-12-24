package com.example.kiranapilates.view


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.kiranapilates.viewmodel.DaftarPengunjungViewModel

@Composable
fun HalamanDaftarPengunjung(
    viewModel: DaftarPengunjungViewModel,
    onNavigateToTambah: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    Text(text = "Ini Halaman Daftar Pengunjung")
}