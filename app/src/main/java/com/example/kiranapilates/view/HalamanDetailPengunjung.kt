package com.example.kiranapilates.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    val context = LocalContext.current
    val pengunjung = viewModel.pengunjungDetail
    var showDeleteDialog by remember { mutableStateOf(false) }

    // --- PALET WARNA TEMA ---
    val PinkBackground = Color(0xFFFCE4EC)
    val PinkPrimary = Color(0xFFF06292)
    val PinkBorder = Color(0xFFF48FB1)
    val PurpleText = Color(0xFF880E4F)
    val WhiteCard = Color(0xFFFFFFFF)

    Scaffold(
        containerColor = PinkBackground, // Background utama Pink
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.detail_pengunjung),
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
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (pengunjung != null) {
                // --- KARTU BIODATA (Putih & Rounded) ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = WhiteCard),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        // Tampilan rincian data
                        DetailRow("ID Pengunjung", pengunjung.id_pengunjung.toString(), PinkPrimary, PurpleText, PinkBorder)
                        DetailRow("Nama Lengkap", pengunjung.nama_lengkap, PinkPrimary, PurpleText, PinkBorder)
                        DetailRow("Nomor HP", pengunjung.no_hp, PinkPrimary, PurpleText, PinkBorder)
                        DetailRow("Tipe", pengunjung.tipe_pengunjung, PinkPrimary, PurpleText, PinkBorder)

                        if (pengunjung.tipe_pengunjung == "Member") {
                            DetailRow("Sisa Kuota", pengunjung.kuota_sisa.toString(), PinkPrimary, PurpleText, PinkBorder)
                        }
                        DetailRow("Total Kunjungan", pengunjung.total_kunjungan.toString(), PinkPrimary, PurpleText, PinkBorder)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // --- TOMBOL EDIT (Pink Penuh) ---
                Button(
                    onClick = { onEditClick(pengunjung.id_pengunjung) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PinkPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        stringResource(R.string.btn_edit),
                        fontWeight = FontWeight.Bold
                    )
                }

                // --- TOMBOL HAPUS (Outline Merah) ---
                OutlinedButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(50.dp),
                    border = BorderStroke(1.dp, Color.Red), // Border Merah
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Red // Teks Merah
                    )
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
            title = {
                Text(
                    stringResource(R.string.hapus_confirm),
                    color = PurpleText,
                    fontWeight = FontWeight.Bold
                )
            },
            containerColor = WhiteCard,
            confirmButton = {
                TextButton(onClick = {
                    // PANGGIL FUNGSI DELETE DENGAN CALLBACK
                    viewModel.deletePengunjung(onSuccess = {
                        // 1. Munculkan Toast
                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()

                        // 2. Kembali ke halaman sebelumnya
                        onBack()
                    })
                    showDeleteDialog = false
                }) {
                    Text("Ya", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal", color = PinkPrimary)
                }
            }
        )
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String,
    labelColor: Color,
    valueColor: Color,
    dividerColor: Color
) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = labelColor // Warna Label (Pink)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            color = valueColor // Warna Isi (Ungu)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Divider(color = dividerColor.copy(alpha = 0.5f)) // Garis pemisah pink tipis
    }
}