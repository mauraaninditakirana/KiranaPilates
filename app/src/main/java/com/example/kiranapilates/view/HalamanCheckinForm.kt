package com.example.kiranapilates.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

    // --- PALET WARNA TEMA ---
    val PinkBackground = Color(0xFFFCE4EC)
    val PinkPrimary = Color(0xFFF06292)
    val PinkBorder = Color(0xFFF48FB1)
    val PurpleText = Color(0xFF880E4F)
    val WhiteContainer = Color(0xFFFFFFFF)

    // Memantau hasil checkin untuk menampilkan pesan
    LaunchedEffect(viewModel.checkinResult) {
        if (viewModel.checkinResult.isNotEmpty()) {
            Toast.makeText(context, viewModel.checkinResult, Toast.LENGTH_SHORT).show()
            if (viewModel.checkinResult.contains("Berhasil", true)) {
                viewModel.checkinResult = ""
            }
        }
    }

    Scaffold(
        containerColor = PinkBackground, // Background utama Pink
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.tambah_checkin),
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
                .padding(24.dp) // Padding agak lega
                .fillMaxSize(), // Full size tapi button tidak dipaksa kebawah
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // --- BAGIAN 1: DROPDOWN PENCARIAN NAMA ---
            Text(
                "Pilih Pengunjung:",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PurpleText
                )
            )

            ExposedDropdownMenuBox(
                expanded = viewModel.isDropdownExpanded,
                onExpandedChange = { viewModel.isDropdownExpanded = !viewModel.isDropdownExpanded }
            ) {
                // Input Pencarian (Bulat & Putih)
                OutlinedTextField(
                    value = viewModel.searchQuery,
                    onValueChange = {
                        viewModel.searchQuery = it
                        viewModel.isDropdownExpanded = true
                    },
                    label = { Text("Ketik Nama Pengunjung", color = PinkPrimary) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.isDropdownExpanded)
                    },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = WhiteContainer,
                        unfocusedContainerColor = WhiteContainer,
                        focusedBorderColor = PinkPrimary,
                        unfocusedBorderColor = PinkBorder,
                        cursorColor = PinkPrimary,
                        focusedLabelColor = PinkPrimary,
                    )
                )

                ExposedDropdownMenu(
                    expanded = viewModel.isDropdownExpanded,
                    onDismissRequest = { viewModel.isDropdownExpanded = false },
                    modifier = Modifier.background(WhiteContainer) // Menu background putih
                ) {
                    if (viewModel.filteredPengunjung.isEmpty()) {
                        DropdownMenuItem(text = { Text("Tidak ditemukan") }, onClick = {})
                    } else {
                        viewModel.filteredPengunjung.forEach { pengunjung ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(pengunjung.nama_lengkap, style = MaterialTheme.typography.bodyLarge, color = PurpleText)
                                        Text(
                                            "Sisa Kuota: ${pengunjung.kuota_sisa} | ${pengunjung.tipe_pengunjung}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = PinkPrimary
                                        )
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

            // Tampilkan Detail Orang yang Dipilih
            viewModel.selectedPengunjung?.let { selected ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = WhiteContainer),
                    elevation = CardDefaults.cardElevation(2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Terpilih: ${selected.nama_lengkap}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = PurpleText
                        )
                        Text(
                            "No HP: ${selected.no_hp}",
                            color = Color.Gray
                        )
                    }
                }
            }

            // --- BAGIAN 2: PILIH SESI ---
            Divider(color = PinkBorder.copy(alpha = 0.5f)) // Garis pemisah pink
            Text(
                "Pilih Sesi:",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PurpleText
                )
            )

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
                            onClick = { viewModel.selectedSesiId = sesi.id_sesi },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = PinkPrimary,
                                unselectedColor = PinkBorder
                            )
                        )
                        Column {
                            Text(
                                text = sesi.nama_sesi,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                                color = PurpleText
                            )
                            Text(
                                text = "${sesi.jam_operasional} - ${sesi.nama_instruktur}",
                                style = MaterialTheme.typography.bodySmall,
                                color = PinkPrimary
                            )
                        }
                    }
                }
            }

            // SAYA HAPUS Spacer(modifier = Modifier.weight(1f)) AGAR BUTTON TIDAK DI BAWAH SEKALI
            Spacer(modifier = Modifier.height(24.dp))

            // --- BAGIAN 3: TOMBOL SUBMIT (Pink & Bulat) ---
            Button(
                onClick = {
                    viewModel.submitCheckin(
                        onSuccess = { onBack() },
                        onQuotaEmpty = { id ->
                            lastPengunjungId = id
                            showQuotaDialog = true
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = viewModel.selectedPengunjungId != null && viewModel.selectedSesiId != null,
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkPrimary,
                    contentColor = Color.White,
                    disabledContainerColor = PinkBorder.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    "Check-in Sekarang",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

    // --- DIALOG KUOTA HABIS (Themed) ---
    if (showQuotaDialog) {
        AlertDialog(
            onDismissRequest = { showQuotaDialog = false },
            containerColor = WhiteContainer,
            title = {
                Text("Kuota Habis!", color = PurpleText, fontWeight = FontWeight.Bold)
            },
            text = {
                Text("Member ini kehabisan kuota. Mau edit data untuk tambah paket?", color = PurpleText)
            },
            confirmButton = {
                TextButton(onClick = {
                    showQuotaDialog = false
                    onEditPengunjung(lastPengunjungId)
                }) {
                    Text("Ya, Edit", color = PinkPrimary, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showQuotaDialog = false }) {
                    Text("Batal", color = Color.Gray)
                }
            }
        )
    }
}