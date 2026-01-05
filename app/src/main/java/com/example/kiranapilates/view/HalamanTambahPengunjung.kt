package com.example.kiranapilates.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kiranapilates.R
import com.example.kiranapilates.viewmodel.TambahPengunjungViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanTambahPengunjung(
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: TambahPengunjungViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val context = LocalContext.current

    val PinkBackground = Color(0xFFFCE4EC)
    val PinkPrimary = Color(0xFFF06292)
    val PinkBorder = Color(0xFFF48FB1)
    val PurpleText = Color(0xFF880E4F)
    val WhiteContainer = Color(0xFFFFFFFF)

    Scaffold(
        containerColor = PinkBackground, // Background
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.tambah_pengunjung),
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
            // FORM INPUT NAMA
            OutlinedTextField(
                value = viewModel.namaInput,
                onValueChange = { viewModel.namaInput = it },
                label = { Text("Nama Lengkap", color = PinkPrimary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp), // Sudut membulat
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = WhiteContainer,
                    unfocusedContainerColor = WhiteContainer,
                    focusedBorderColor = PinkPrimary,
                    unfocusedBorderColor = PinkBorder,
                    cursorColor = PinkPrimary,
                    focusedLabelColor = PinkPrimary,
                ),
                singleLine = true
            )

            //  FORM INPUT NO HP
            OutlinedTextField(
                value = viewModel.noHpInput,
                onValueChange = { viewModel.noHpInput = it },
                label = { Text("Nomor HP", color = PinkPrimary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp), // Sudut membulat
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = WhiteContainer,
                    unfocusedContainerColor = WhiteContainer,
                    focusedBorderColor = PinkPrimary,
                    unfocusedBorderColor = PinkBorder,
                    cursorColor = PinkPrimary,
                    focusedLabelColor = PinkPrimary,
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // PILIHAN TIPE PENGUNJUNG
            Text(
                "Pilih Tipe Pengunjung:",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = PurpleText
                )
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Radio Button Member
                RadioButton(
                    selected = viewModel.tipeInput == "Member",
                    onClick = { viewModel.tipeInput = "Member" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = PinkPrimary, // Warna saat dipilih
                        unselectedColor = PinkBorder // Warna saat tidak dipilih
                    )
                )
                Text(
                    text = "Member",
                    modifier = Modifier.padding(start = 8.dp),
                    color = PurpleText,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.width(24.dp))

                // Radio Button Guest
                RadioButton(
                    selected = viewModel.tipeInput == "Guest",
                    onClick = { viewModel.tipeInput = "Guest" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = PinkPrimary,
                        unselectedColor = PinkBorder
                    )
                )
                Text(
                    text = "Guest",
                    modifier = Modifier.padding(start = 8.dp),
                    color = PurpleText,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // TOMBOL SIMPAN
            Button(
                onClick = {
                    // Cek apakah nama atau no hp kosong (atau cuma spasi)
                    if (viewModel.namaInput.isBlank() || viewModel.noHpInput.isBlank()) {
                        // Jika kosong, tampilkan pesan error
                        Toast.makeText(context, "Nama dan Nomor HP wajib diisi!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Jika terisi, baru jalankan proses simpan
                        viewModel.simpanPengunjung(
                            onSuccess = {
                                Toast.makeText(context, context.getString(R.string.sukses_tambah), Toast.LENGTH_SHORT).show()
                                onSuccess()
                            },
                            onError = { msg ->
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                },
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
                    stringResource(R.string.btn_simpan),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}