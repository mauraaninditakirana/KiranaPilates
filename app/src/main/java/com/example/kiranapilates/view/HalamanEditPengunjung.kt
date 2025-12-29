package com.example.kiranapilates.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kiranapilates.R
import com.example.kiranapilates.viewmodel.EditPengunjungViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEditPengunjung(
    token: String,
    onBack: () -> Unit,
    viewModel: EditPengunjungViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_pengunjung)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.namaInput,
                onValueChange = { viewModel.namaInput = it },
                label = { Text("Nama Lengkap") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.noHpInput,
                onValueChange = { viewModel.noHpInput = it },
                label = { Text("Nomor HP") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.tipeInput,
                onValueChange = { viewModel.tipeInput = it },
                label = { Text("Tipe (Member/Guest)") },
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.tipeInput == "Member") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = viewModel.isTambahPaket,
                        onCheckedChange = { viewModel.isTambahPaket = it }
                    )
                    Text(text = "Tambah Paket (+10 Kuota)")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        // 1. CEK LOGCAT: Lihat apakah token kosong atau ada isinya
                        println("DEBUG TOKEN DI EDIT: '$token'")

                        // 2. LOGIKA PENGAMAN: Hanya kirim jika token tidak kosong
                        if (token.isNotEmpty()) {
                            println("DEBUG: Mengirim request update...")
                            viewModel.updatePengunjung(token, onBack)
                        } else {
                            println("ERROR: Token kosong! Update dibatalkan.")
                            // Opsional: Kamu bisa tambahkan Toast disini jika mau
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Simpan")
                }

                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Batal")
                }
            }
        }
    }
}