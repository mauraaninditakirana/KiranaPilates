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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tambah_pengunjung)) },
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
            OutlinedTextField(
                value = viewModel.namaInput,
                onValueChange = { viewModel.namaInput = it },
                label = { Text("Nama Lengkap") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.noHpInput,
                onValueChange = { viewModel.noHpInput = it },
                label = { Text("Nomor HP") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Pilih Tipe Pengunjung:")
            Row {
                RadioButton(
                    selected = viewModel.tipeInput == "Member",
                    onClick = { viewModel.tipeInput = "Member" }
                )
                Text("Member", modifier = Modifier.padding(top = 12.dp))
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = viewModel.tipeInput == "Guest",
                    onClick = { viewModel.tipeInput = "Guest" }
                )
                Text("Guest", modifier = Modifier.padding(top = 12.dp))
            }

            Button(
                onClick = {
                    viewModel.simpanPengunjung(
                        onSuccess = {
                            Toast.makeText(context, context.getString(R.string.sukses_tambah), Toast.LENGTH_SHORT).show()
                            onSuccess()
                        },
                        onError = { msg ->
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.btn_simpan))
            }
        }
    }
}