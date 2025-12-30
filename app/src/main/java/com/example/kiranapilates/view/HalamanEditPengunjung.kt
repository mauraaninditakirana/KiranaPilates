package com.example.kiranapilates.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kiranapilates.R
import com.example.kiranapilates.viewmodel.EditPengunjungViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanEditPengunjung(
    token: String,
    onBack: () -> Unit,
    viewModel: EditPengunjungViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // --- PALET WARNA TEMA ---
    val PinkBackground = Color(0xFFFCE4EC)
    val PinkPrimary = Color(0xFFF06292)
    val PinkBorder = Color(0xFFF48FB1)
    val PurpleText = Color(0xFF880E4F)
    val WhiteContainer = Color(0xFFFFFFFF)

    Scaffold(
        containerColor = PinkBackground, // Background utama Pink
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.edit_pengunjung),
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
            // --- INPUT NAMA (Bulat & Pink) ---
            OutlinedTextField(
                value = viewModel.namaInput,
                onValueChange = { viewModel.namaInput = it },
                label = { Text("Nama Lengkap", color = PinkPrimary) },
                modifier = Modifier.fillMaxWidth(),
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

            // --- INPUT HP (Bulat & Pink) ---
            OutlinedTextField(
                value = viewModel.noHpInput,
                onValueChange = { viewModel.noHpInput = it },
                label = { Text("Nomor HP", color = PinkPrimary) },
                modifier = Modifier.fillMaxWidth(),
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

            // --- INPUT TIPE (Bulat & Pink) ---
            OutlinedTextField(
                value = viewModel.tipeInput,
                onValueChange = { viewModel.tipeInput = it },
                label = { Text("Tipe (Member/Guest)", color = PinkPrimary) },
                modifier = Modifier.fillMaxWidth(),
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

            if (viewModel.tipeInput == "Member") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    // Checkbox Pink
                    Checkbox(
                        checked = viewModel.isTambahPaket,
                        onCheckedChange = { viewModel.isTambahPaket = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = PinkPrimary,
                            uncheckedColor = PinkBorder
                        )
                    )
                    Text(
                        text = "Tambah Paket (+10 Kuota)",
                        color = PurpleText,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // --- TOMBOL SIMPAN (Pink Penuh) ---
                Button(
                    onClick = {
                        // LOGIKA ASLI (JANGAN DIUBAH)
                        println("DEBUG TOKEN DI EDIT: '$token'")

                        if (token.isNotEmpty()) {
                            println("DEBUG: Mengirim request update...")
                            viewModel.updatePengunjung(token, onBack)
                        } else {
                            println("ERROR: Token kosong! Update dibatalkan.")
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PinkPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Simpan", fontWeight = FontWeight.Bold)
                }

                // --- TOMBOL BATAL (Outline Pink) ---
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(50.dp),
                    border = BorderStroke(1.dp, PinkPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PinkPrimary
                    )
                ) {
                    Text("Batal")
                }
            }
        }
    }
}