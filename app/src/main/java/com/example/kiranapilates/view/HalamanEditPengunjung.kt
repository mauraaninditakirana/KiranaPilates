package com.example.kiranapilates.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.kiranapilates.viewmodel.EditPengunjungViewModel

@Composable
fun HalamanEditPengunjung(
    viewModel: EditPengunjungViewModel,
    onNavigateBack: () -> Unit
) {
    // Kerangka tampilan edit
    Text("Halaman Edit Pengunjung")
}