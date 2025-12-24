package com.example.kiranapilates.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kiranapilates.viewmodel.TambahPengunjungViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanTambahPengunjung(
    viewModel: TambahPengunjungViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Tambah Pengunjung") })
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Input Nama
            OutlinedTextField(
                value = uiState.nama_lengkap,
                onValueChange = { viewModel.updateUiState(it, uiState.no_hp, uiState.tipe_pengunjung) },
                label = { Text("Nama Lengkap") },
                modifier = Modifier.fillMaxWidth()
            )

            // Input No HP
            OutlinedTextField(
                value = uiState.no_hp,
                onValueChange = { viewModel.updateUiState(uiState.nama_lengkap, it, uiState.tipe_pengunjung) },
                label = { Text("No HP") },
                modifier = Modifier.fillMaxWidth()
            )

            // Tombol Simpan
            Button(
                onClick = { },
                enabled = uiState.isEntryValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan")
            }
        }
    }
}