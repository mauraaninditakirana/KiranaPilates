package com.example.kiranapilates.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kiranapilates.R
import com.example.kiranapilates.viewmodel.DetailPengunjungViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDetailPengunjung(
    onEditClick: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: DetailPengunjungViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val pengunjung = viewModel.pengunjungDetail
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.detail_pengunjung)) },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (pengunjung != null) {
                // Tampilan rincian data
                DetailRow("ID Pengunjung", pengunjung.id_pengunjung.toString())
                DetailRow("Nama Lengkap", pengunjung.nama_lengkap)
                DetailRow("Nomor HP", pengunjung.no_hp)
                DetailRow("Tipe", pengunjung.tipe_pengunjung)

                if (pengunjung.tipe_pengunjung == "Member") {
                    DetailRow("Sisa Kuota", pengunjung.kuota_sisa.toString())
                }
                DetailRow("Total Kunjungan", pengunjung.total_kunjungan.toString())

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onEditClick(pengunjung.id_pengunjung) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.btn_edit))
                }

                OutlinedButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(R.string.btn_hapus))
                }
            }
        }
    }

    // Dialog Konfirmasi Hapus
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.hapus_confirm)) },
            confirmButton = {
                TextButton(onClick = {
                    pengunjung?.let { viewModel.deletePengunjung(it.id_pengunjung, onBack) }
                    showDeleteDialog = false
                }) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
        Divider(modifier = Modifier.padding(top = 4.dp))
    }
}