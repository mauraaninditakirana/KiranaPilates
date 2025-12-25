package com.example.kiranapilates.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kiranapilates.viewmodel.CheckinViewModel

@Composable
fun HalamanCheckinForm(
    viewModel: CheckinViewModel,
    onNavigateBack: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text("Tambah Check-in", style = MaterialTheme.typography.headlineSmall)

            // Dropdown Search Nama Pengunjung
            OutlinedTextField(
                value = viewModel.namaSearch,
                onValueChange = { viewModel.namaSearch = it },
                label = { Text("Cari Nama Pengunjung (ex: Maura)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Radio Button Sesi
            Text("Pilih Sesi:")
            val opsiSesi = listOf("Pagi", "Sore", "Malam")
            opsiSesi.forEach { sesi ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    RadioButton(
                        selected = (viewModel.sesiTerpilih == sesi),
                        onClick = { viewModel.sesiTerpilih = sesi }
                    )
                    Text(sesi, modifier = Modifier.padding(top = 12.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Fungsi Submit Check-in */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}