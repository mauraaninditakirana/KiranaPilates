package com.example.kiranapilates.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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

    // Memantau hasil checkin untuk menampilkan pesan
    LaunchedEffect(viewModel.checkinResult) {
        if (viewModel.checkinResult.isNotEmpty()) {
            Toast.makeText(context, viewModel.checkinResult, Toast.LENGTH_SHORT).show()
            // Jika berhasil, reset pesan agar toast tidak muncul terus
            if (viewModel.checkinResult.contains("Berhasil", true)) {
                viewModel.checkinResult = "" // Reset
            }
        }
    }

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
            // --- BAGIAN 1: DROPDOWN PENCARIAN NAMA ---
            Text("Pilih Pengunjung:", style = MaterialTheme.typography.titleMedium)

            ExposedDropdownMenuBox(
                expanded = viewModel.isDropdownExpanded,
                onExpandedChange = { viewModel.isDropdownExpanded = !viewModel.isDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = viewModel.searchQuery,
                    onValueChange = {
                        viewModel.searchQuery = it
                        viewModel.isDropdownExpanded = true
                    },
                    label = { Text("Ketik Nama Pengunjung") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.isDropdownExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )

                ExposedDropdownMenu(
                    expanded = viewModel.isDropdownExpanded,
                    onDismissRequest = { viewModel.isDropdownExpanded = false }
                ) {
                    if (viewModel.filteredPengunjung.isEmpty()) {
                        DropdownMenuItem(text = { Text("Tidak ditemukan") }, onClick = {})
                    } else {
                        viewModel.filteredPengunjung.forEach { pengunjung ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(pengunjung.nama_lengkap, style = MaterialTheme.typography.bodyLarge)
                                        Text("Sisa Kuota: ${pengunjung.kuota_sisa} | ${pengunjung.tipe_pengunjung}", style = MaterialTheme.typography.bodySmall)
                                    }
                                },
                                onClick = {
                                    viewModel.selectPengunjung(pengunjung)
                                }
                            )
                        }
                    }
                }
            }

            // Tampilkan Detail Orang yang Dipilih (Opsional tapi membantu)
            viewModel.selectedPengunjung?.let { selected ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Terpilih: ${selected.nama_lengkap}", style = MaterialTheme.typography.labelLarge)
                        Text("No HP: ${selected.no_hp}")
                    }
                }
            }


            // --- BAGIAN 2: PILIH SESI ---
            Divider()
            Text("Pilih Sesi:", style = MaterialTheme.typography.titleMedium)

            Column {
                viewModel.listSesi.forEach { sesi ->
                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = viewModel.selectedSesiId == sesi.id_sesi,
                            onClick = { viewModel.selectedSesiId = sesi.id_sesi }
                        )
                        Column {
                            Text(text = sesi.nama_sesi, style = MaterialTheme.typography.bodyLarge)
                            Text(text = "${sesi.jam_operasional} - ${sesi.nama_instruktur}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- BAGIAN 3: TOMBOL SUBMIT ---
            Button(
                onClick = {
                    viewModel.submitCheckin(
                        onSuccess = {
                            // Navigasi onBack akan dipanggil setelah Toast muncul (lihat logic di atas/bawah)
                            onBack()
                        },
                        onQuotaEmpty = { id ->
                            lastPengunjungId = id
                            showQuotaDialog = true
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.selectedPengunjungId != null && viewModel.selectedSesiId != null
            ) {
                Text("Check-in Sekarang")
            }
        }
    }

    // --- DIALOG KUOTA HABIS ---
    if (showQuotaDialog) {
        AlertDialog(
            onDismissRequest = { showQuotaDialog = false },
            title = { Text("Kuota Habis!") },
            text = { Text("Member ini kehabisan kuota. Mau edit data untuk tambah paket?") },
            confirmButton = {
                TextButton(onClick = {
                    showQuotaDialog = false
                    onEditPengunjung(lastPengunjungId)
                }) { Text("Ya, Edit") }
            },
            dismissButton = {
                TextButton(onClick = { showQuotaDialog = false }) { Text("Batal") }
            }
        )
    }
}