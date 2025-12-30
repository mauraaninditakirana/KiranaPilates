package com.example.kiranapilates.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    // PALET WARNA TEMA
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
                        stringResource(R.string.menu_checkin),
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
                onClick = onAddCheckin,
                containerColor = PinkPrimary,
                contentColor = Color.White,
                shape = RoundedCornerShape(50.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Check-in")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // FILTER TANGGAL
            OutlinedTextField(
                value = viewModel.selectedDate,
                onValueChange = { viewModel.onDateSelected(it) },
                label = { Text("Filter Tanggal (yyyy-mm-dd)", color = PinkPrimary) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        tint = PinkPrimary
                    )
                },
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

            Text(
                text = "Pilih Sesi untuk lihat riwayat:",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = PurpleText
                )
            )

            // 3 CARD SESI UTAMA
            CardSesiCheckin("Pagi", 1, viewModel.selectedDate, onSesiClick)
            CardSesiCheckin("Sore", 2, viewModel.selectedDate, onSesiClick)
            CardSesiCheckin("Malam", 3, viewModel.selectedDate, onSesiClick)
        }
    }
}

@Composable
fun CardSesiCheckin(nama: String, id: Int, tanggal: String, onClick: (Int, String) -> Unit) {
    // Warna Lokal
    val WhiteCard = Color(0xFFFFFFFF)
    val PurpleText = Color(0xFF880E4F)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable { onClick(id, tanggal) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(20.dp), // Sudut membulat
        colors = CardDefaults.cardColors(containerColor = WhiteCard)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Sesi $nama",
                modifier = Modifier.padding(start = 24.dp),
                style = MaterialTheme.typography.headlineSmall.copy( // Font Besar
                    fontWeight = FontWeight.Bold,
                    color = PurpleText
                )
            )
        }
    }
}