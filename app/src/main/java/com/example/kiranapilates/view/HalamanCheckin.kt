package com.example.kiranapilates.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.kiranapilates.viewmodel.CheckinViewModel

@Composable
fun HalamanCheckin(
    viewModel: CheckinViewModel,
    onSesiClick: (String, String) -> Unit, // Mengirim ID Sesi dan Tanggal ke Riwayat
    onTambahClick: () -> Unit, // Ke Halaman 10
    onBack: () -> Unit
) {
    // UI: Filter Tanggal + 3 Card Sesi + FloatingActionButton (+)
    Text("Halaman Menu Check-in (Halaman 9)")
}