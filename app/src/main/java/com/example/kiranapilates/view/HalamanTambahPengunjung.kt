package com.example.kiranapilates.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.kiranapilates.R
import com.example.kiranapilates.viewmodel.TambahPengunjungViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanTambahPengunjung(
    viewModel: TambahPengunjungViewModel,
    onNavigateBack: () -> Unit
) {
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_tambah_pengunjung)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(paddingMedium)
                .fillMaxSize()
        ) {
            // Field Nama Lengkap
            OutlinedTextField(
                value = viewModel.nama_lengkap,
                onValueChange = { viewModel.updateNama(it) },
                label = { Text(stringResource(R.string.label_nama_lengkap)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(paddingMedium))

            // Field No HP
            OutlinedTextField(
                value = viewModel.nohp,
                onValueChange = { viewModel.updateNoHp(it) },
                label = { Text(stringResource(R.string.label_nohp)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(paddingMedium))

            // Dropdown/Radio Tipe Pengunjung (Sederhana dulu)
            Text(stringResource(R.string.label_tipe))
            // ... (Bisa ditambah RadioButton di sini)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.simpanPengunjung()
                    showDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.btn_submit))
            }

            // POP-UP BERHASIL
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            onNavigateBack() // Kembali ke Daftar
                        }) { Text("OK") }
                    },
                    title = { Text("Notifikasi") },
                    text = { Text(stringResource(R.string.msg_success)) }
                )
            }
        }
    }
}