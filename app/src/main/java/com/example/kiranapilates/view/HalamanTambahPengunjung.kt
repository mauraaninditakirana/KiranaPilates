package com.example.kiranapilates.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kiranapilates.viewmodel.TambahPengunjungViewModel

@Composable
fun HalamanTambahPengunjung(
    viewModel: TambahPengunjungViewModel,
    onNavigateBack: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("Halaman Form Tambah Pengunjung")

        }
    }
}