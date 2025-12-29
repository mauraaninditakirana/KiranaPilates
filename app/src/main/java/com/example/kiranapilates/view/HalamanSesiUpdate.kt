package com.example.kiranapilates.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kiranapilates.R
import com.example.kiranapilates.viewmodel.SesiUpdateViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanSesiUpdate(
    token: String,
    onBack: () -> Unit,
    viewModel: SesiUpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_sesi)) },
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
            // Menampilkan Nama Sesi (Pagi/Sore) sebagai Judul (Read Only)
            // Jika ingin bisa diedit juga, ganti jadi OutlinedTextField seperti bawahnya
            Text(
                text = "Edit Sesi: ${viewModel.namaSesi}",
                style = MaterialTheme.typography.titleLarge
            )

            // Input Jam (Otomatis terisi dari loadDataLama)
            OutlinedTextField(
                value = viewModel.jamInput,
                onValueChange = { viewModel.jamInput = it },
                label = { Text("Jam Operasional") },
                modifier = Modifier.fillMaxWidth()
            )

            // Input Instruktur (Otomatis terisi dari loadDataLama)
            OutlinedTextField(
                value = viewModel.instrukturInput,
                onValueChange = { viewModel.instrukturInput = it },
                label = { Text("Nama Instruktur") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.updateSesi(token, onBack) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.btn_simpan))
                }

                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.btn_batal))
                }
            }
        }
    }
}