package com.example.kiranapilates.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kiranapilates.R
import com.example.kiranapilates.viewmodel.CheckinFormViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanCheckinForm(
    onBack: () -> Unit,
    onEditPengunjung: (Int) -> Unit,
    viewModel: CheckinFormViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val context = LocalContext.current
    var showQuotaDialog by remember { mutableStateOf(false) }
    var lastPengunjungId by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tambah_checkin)) },
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
            // Dropdown Sederhana (Bisa dikembangkan menjadi Searchable)
            Text("Pilih Pengunjung:")
            // ... Logic dropdown listPengunjung ...

            Text("Pilih Sesi:")
            viewModel.listSesi.forEach { sesi ->
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewModel.selectedSesiId == sesi.id_sesi,
                        onClick = { viewModel.selectedSesiId = sesi.id_sesi }
                    )
                    Text(sesi.nama_sesi)
                }
            }

            Button(
                onClick = {
                    viewModel.submitCheckin(
                        onSuccess = {
                            Toast.makeText(context, "Check-in Berhasil", Toast.LENGTH_SHORT).show()
                            onBack()
                        },
                        onQuotaEmpty = { id ->
                            lastPengunjungId = id
                            showQuotaDialog = true
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.login_button)) // Menggunakan teks 'Submit'
            }
        }
    }

    // Dialog Kuota Habis
    if (showQuotaDialog) {
        AlertDialog(
            onDismissRequest = { showQuotaDialog = false },
            title = { Text("Kuota Member Habis!") },
            text = { Text("Apakah Anda ingin mengedit data pengunjung untuk menambah paket?") },
            confirmButton = {
                TextButton(onClick = {
                    showQuotaDialog = false
                    onEditPengunjung(lastPengunjungId)
                }) { Text("Edit") }
            },
            dismissButton = {
                TextButton(onClick = { showQuotaDialog = false; onBack() }) { Text("Batal") }
            }
        )
    }
}