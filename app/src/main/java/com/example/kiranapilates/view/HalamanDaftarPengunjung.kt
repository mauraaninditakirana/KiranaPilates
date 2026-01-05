package com.example.kiranapilates.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    // --- PALET WARNA TEMA ---
    val PinkBackground = Color(0xFFFCE4EC)
    val PinkPrimary = Color(0xFFF06292)
    val PinkBorder = Color(0xFFF48FB1)
    val PurpleText = Color(0xFF880E4F)
    val WhiteContainer = Color(0xFFFFFFFF)

    LaunchedEffect(Unit) {
        viewModel.getPengunjung()
    }

    Scaffold(
        containerColor = PinkBackground, // Background utama
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.daftar_pengunjung),
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onTambahPengunjung,
                containerColor = PinkPrimary,
                contentColor = Color.White,
                shape = RoundedCornerShape(50.dp) // Bulat Penuh
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            // 2. SEARCH BAR
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // Padding luar search bar
                placeholder = { Text(stringResource(R.string.search_hint), color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = PinkPrimary
                    )
                },
                shape = RoundedCornerShape(25.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = WhiteContainer,
                    unfocusedContainerColor = WhiteContainer,
                    focusedBorderColor = PinkPrimary,
                    unfocusedBorderColor = PinkBorder,
                    cursorColor = PinkPrimary,
                )
            )

            // 3. DAFTAR PENGUNJUNG
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(), // Mengambil sisa ruang ke bawah
                // contentPadding agar item terbawah tidak tertutup FAB (+80dp)
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
    // Warna lokal untuk kartu
    val WhiteCard = Color(0xFFFFFFFF)
    val PinkPrimary = Color(0xFFF06292)
    val PurpleText = Color(0xFF880E4F)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDetailClick(pengunjung.id_pengunjung) },
        elevation = CardDefaults.cardElevation(4.dp), // Bayangan
        shape = RoundedCornerShape(20.dp), // Sudut membulat
        colors = CardDefaults.cardColors(containerColor = WhiteCard)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Nama Pengunjung
            Text(
                text = pengunjung.nama_lengkap,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = PurpleText
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Tipe Pengunjung
            Text(
                text = "Tipe: ${pengunjung.tipe_pengunjung}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = PinkPrimary
            )
        }
    }
}