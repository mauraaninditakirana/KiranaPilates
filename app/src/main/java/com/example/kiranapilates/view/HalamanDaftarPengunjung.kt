package com.example.kiranapilates.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kiranapilates.R
import com.example.kiranapilates.modeldata.Pengunjung
import com.example.kiranapilates.viewmodel.DaftarPengunjungViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDaftarPengunjung(
    onTambahPengunjung: () -> Unit,
    onDetailClick: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: DaftarPengunjungViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getPengunjung()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.daftar_pengunjung)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onTambahPengunjung) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Fitur Search di paling atas
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(stringResource(R.string.search_hint)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.filteredPengunjung) { pengunjung ->
                    ItemPengunjungCard(pengunjung, onDetailClick)
                }
            }
        }
    }
}

@Composable
fun ItemPengunjungCard(pengunjung: Pengunjung, onDetailClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDetailClick(pengunjung.id_pengunjung) },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = pengunjung.nama_lengkap, style = MaterialTheme.typography.titleMedium)
            Text(text = "Tipe: ${pengunjung.tipe_pengunjung}", style = MaterialTheme.typography.bodySmall)
        }
    }
}