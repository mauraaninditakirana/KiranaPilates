package com.example.kiranapilates.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kiranapilates.viewmodel.CheckinViewModel

@Composable
fun HalamanCheckinMenu(
    viewModel: CheckinViewModel,
    onSesiClick: (String, String) -> Unit,
    onTambahClick: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onTambahClick) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text("Filter Tanggal:", style = MaterialTheme.typography.titleMedium)

            // Filter Tanggal (Sederhana dulu agar bisa run)
            OutlinedButton(onClick = { /* Nanti muncul DatePicker */ }) {
                Text(viewModel.selectedDate)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3 Card Sesi (Pagi, Sore, Malam)
            val daftarSesi = listOf("Pagi", "Sore", "Malam")
            daftarSesi.forEachIndexed { index, sesi ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onSesiClick((index + 1).toString(), viewModel.selectedDate) }
                ) {
                    Text(text = "Sesi $sesi", modifier = Modifier.padding(24.dp))
                }
            }
        }
    }
}