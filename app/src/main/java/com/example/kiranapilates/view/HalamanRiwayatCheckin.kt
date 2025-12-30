package com.example.kiranapilates.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kiranapilates.viewmodel.RiwayatCheckinViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanRiwayatCheckin(
    sesiId: Int, // Parameter dari navigasi
    tanggal: String, // Parameter dari navigasi
    onBack: () -> Unit,
    viewModel: RiwayatCheckinViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // Load data saat halaman dibuka
    LaunchedEffect(sesiId, tanggal) {
        viewModel.updateFilter(sesiId, tanggal)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Check-In") },
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
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Daftar Hadir Sesi $sesiId - Tanggal: $tanggal",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            when {
                viewModel.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                viewModel.errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = viewModel.errorMessage ?: "Terjadi kesalahan",
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.loadRiwayatCheckin() }) {
                                Text("Coba Lagi")
                            }
                        }
                    }
                }

                viewModel.riwayatList.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Belum ada data check-in")
                    }
                }

                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(viewModel.riwayatList) { riwayat ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                ListItem(
                                    headlineContent = {
                                        Text(
                                            text = riwayat.nama_lengkap,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    },
                                    supportingContent = {
                                        Column {
                                            Text("ID: ${riwayat.id_pengunjung}")
                                            Text("Tipe: ${riwayat.tipe_saat_checkin}")
                                        }
                                    },
                                    trailingContent = {
                                        Text(
                                            text = riwayat.tanggal_checkin.split(" ").getOrNull(1) ?: "",
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}