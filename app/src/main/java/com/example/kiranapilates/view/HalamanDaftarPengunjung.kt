package com.example.kiranapilates.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.kiranapilates.R // Pastikan import R projectmu
import com.example.kiranapilates.viewmodel.DaftarPengunjungViewModel
import com.example.kiranapilates.viewmodel.DaftarUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDaftarPengunjung(
    viewModel: DaftarPengunjungViewModel,
    onDetailClick: (String) -> Unit,
    onTambahClick: () -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getListPengunjung("ISI_TOKEN_ADMIN_MU")
    }
    // Ambil nilai dari dimens.xml
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val paddingSmall = dimensionResource(id = R.dimen.padding_small)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_daftar_pengunjung)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_content_desc)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onTambahClick) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_content_desc)
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // SEARCH FIELD menggunakan Strings & Dimens
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMedium),
                placeholder = { Text(stringResource(R.string.search_placeholder)) },
                leadingIcon = { Icon(Icons.Default.Search, null) }
            )

            when (val state = viewModel.daftarUiState) {
                is DaftarUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is DaftarUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.error_data))
                    }
                }
                is DaftarUiState.Success -> {
                    val filteredList = state.pengunjung.filter {
                        it.nama_lengkap.contains(viewModel.searchQuery, ignoreCase = true)
                    }

                    if (filteredList.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(stringResource(R.string.empty_data))
                        }
                    } else {
                        LazyColumn(Modifier.fillMaxSize()) {
                            items(filteredList) { pengunjung ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = paddingMedium, vertical = paddingSmall)
                                        .clickable { onDetailClick(pengunjung.id_pengunjung) },
                                    elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_elevation))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(paddingMedium),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(Modifier.weight(1f)) {
                                            Text(pengunjung.nama_lengkap, style = MaterialTheme.typography.titleLarge)
                                            // Menggunakan String format untuk tipe
                                            Text(
                                                text = stringResource(R.string.type_label, pengunjung.tipe_pengunjung),
                                                color = Color.Gray
                                            )
                                        }
                                        Icon(Icons.Default.KeyboardArrowRight, null)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}