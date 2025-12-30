package com.example.kiranapilates.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kiranapilates.R
import com.example.kiranapilates.viewmodel.SesiUpdateViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanSesiUpdate(
    token: String,
    onBack: () -> Unit,
    viewModel: SesiUpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                        stringResource(R.string.edit_sesi),
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
                            tint = PinkBackground // Icon back warna ungu
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Nama Sesi
            Text(
                text = "Edit Sesi: ${viewModel.namaSesi}",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = PurpleText
            )

            // Input Jam (Rounded & Pink Theme)
            OutlinedTextField(
                value = viewModel.jamInput,
                onValueChange = { viewModel.jamInput = it },
                label = { Text("Jam Operasional", color = PinkPrimary) },
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

            // Input Instruktur (Rounded & Pink Theme)
            OutlinedTextField(
                value = viewModel.instrukturInput,
                onValueChange = { viewModel.instrukturInput = it },
                label = { Text("Nama Instruktur", color = PinkPrimary) },
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

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Aksi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Tombol Simpan (Pink Penuh)
                Button(
                    onClick = { viewModel.updateSesi(token, onBack) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PinkPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        stringResource(R.string.btn_simpan),
                        fontWeight = FontWeight.Bold
                    )
                }

                // Tombol Batal (Outline Pink)
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
                    Text(stringResource(R.string.btn_batal))
                }
            }
        }
    }
}