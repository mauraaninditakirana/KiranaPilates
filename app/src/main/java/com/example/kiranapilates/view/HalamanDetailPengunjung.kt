package com.example.kiranapilates.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.kiranapilates.viewmodel.DetailPengunjungViewModel

@Composable
fun HalamanDetailPengunjung(
    viewModel: DetailPengunjungViewModel,
    onNavigateBack: () -> Unit
) {
    Text(text = "Halaman Detail Pengunjung")
}