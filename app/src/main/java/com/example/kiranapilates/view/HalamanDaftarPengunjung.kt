package com.example.kiranapilates.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kiranapilates.viewmodel.DaftarPengunjungViewModel

@Composable
fun HalamanDaftarPengunjung(
    viewModel: DaftarPengunjungViewModel,
    onNavigateToTambah: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("Halaman Daftar Pengunjung")

        }
    }
}