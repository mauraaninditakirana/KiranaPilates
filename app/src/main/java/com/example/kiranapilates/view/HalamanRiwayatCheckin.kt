package com.example.kiranapilates.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    // --- PALET WARNA TEMA ---
    val PinkBackground = Color(0xFFFCE4EC)
    val PinkPrimary = Color(0xFFF06292)
    val PurpleText = Color(0xFF880E4F)
    val WhiteCard = Color(0xFFFFFFFF)

    // Load data saat halaman dibuka (LOGIKA TETAP)
    LaunchedEffect(sesiId, tanggal) {
        viewModel.updateFilter(sesiId, tanggal)
    }

    Scaffold(
        containerColor = PinkBackground, // Background utama Pink
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Riwayat Check-In",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PinkBackground
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = PinkBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PurpleText
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp) // Padding agak lega
        ) {
            // Header Info Filter
            Text(
                text = "Daftar Hadir Sesi $sesiId - Tanggal: $tanggal",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = PurpleText
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            when {
                viewModel.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = PinkPrimary)
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
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.loadRiwayatCheckin() },
                                colors = ButtonDefaults.buttonColors(containerColor = PinkPrimary)
                            ) {
                                Text("Coba Lagi", color = Color.White)
                            }
                        }
                    }
                }

                viewModel.riwayatList.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Belum ada data check-in",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(viewModel.riwayatList) { riwayat ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                shape = RoundedCornerShape(20.dp), // Sudut membulat
                                colors = CardDefaults.cardColors(containerColor = WhiteCard) // Putih Bersih
                            ) {
                                ListItem(
                                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                    headlineContent = {
                                        Text(
                                            text = riwayat.nama_lengkap,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = PurpleText
                                        )
                                    },
                                    supportingContent = {
                                        Column {
                                            Text(
                                                "ID: ${riwayat.id_pengunjung}",
                                                color = Color.Gray,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                            Text(
                                                "Tipe: ${riwayat.tipe_saat_checkin}",
                                                color = PinkPrimary, // Tipe warna Pink
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                        }
                                    },
                                    trailingContent = {
                                        Text(
                                            text = riwayat.tanggal_checkin.split(" ").getOrNull(1) ?: "",
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = PurpleText
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