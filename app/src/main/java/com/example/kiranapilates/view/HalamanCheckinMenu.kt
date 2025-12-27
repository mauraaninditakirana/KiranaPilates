package com.example.kiranapilates.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kiranapilates.R
import com.example.kiranapilates.viewmodel.CheckinMenuViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanCheckinMenu(
    onSesiClick: (Int, String) -> Unit,
    onAddCheckin: () -> Unit,
    onBack: () -> Unit,
    viewModel: CheckinMenuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.menu_checkin)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCheckin) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Check-in")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Filter Tanggal di atas Card
            OutlinedTextField(
                value = viewModel.selectedDate,
                onValueChange = { viewModel.onDateSelected(it) },
                label = { Text("Filter Tanggal (yyyy-mm-dd)") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) }
            )

            Text(text = "Pilih Sesi untuk lihat riwayat:", style = MaterialTheme.typography.titleMedium)

            // 3 Card Sesi Utama
            CardSesiCheckin("Pagi", 1, viewModel.selectedDate, onSesiClick)
            CardSesiCheckin("Sore", 2, viewModel.selectedDate, onSesiClick)
            CardSesiCheckin("Malam", 3, viewModel.selectedDate, onSesiClick)
        }
    }
}

@Composable
fun CardSesiCheckin(nama: String, id: Int, tanggal: String, onClick: (Int, String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick(id, tanggal) },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
            Text(text = "Sesi $nama", modifier = Modifier.padding(start = 16.dp), style = MaterialTheme.typography.titleLarge)
        }
    }
}