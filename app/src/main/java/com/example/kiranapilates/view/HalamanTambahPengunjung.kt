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
                title = { Text(stringResource(R.string.title_tambah_pengunjung)) }, // Pakai string milikmu
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_content_desc) // Ini yang tadi salah
                        )
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
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(paddingMedium))

            // Field Nomor HP
            OutlinedTextField(
                value = viewModel.nohp,
                onValueChange = { viewModel.updateNoHp(it) },
                label = { Text(stringResource(R.string.label_nohp)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(paddingMedium))

            // Label Tipe
            Text(text = stringResource(R.string.label_tipe), style = MaterialTheme.typography.bodyLarge)

            // Spacer untuk mendorong button ke bawah
            Spacer(modifier = Modifier.weight(1f))

            // Button Submit
            Button(
                onClick = {
                    viewModel.simpanPengunjung()
                    showDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.btn_submit))
            }

            // POP-UP NOTIFIKASI BERHASIL
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            onNavigateBack() // Kembali ke Daftar Pengunjung
                        }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Notifikasi") },
                    text = { Text(stringResource(R.string.msg_success)) }
                )
            }
        }
    }
}