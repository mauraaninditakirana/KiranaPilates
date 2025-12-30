package com.example.kiranapilates.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import com.example.kiranapilates.modeldata.Sesi
import com.example.kiranapilates.viewmodel.SesiViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanSesi(
    onEditSesi: (Int) -> Unit,
    onBack: () -> Unit,
    viewModel: SesiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    val PinkBackground = Color(0xFFFCE4EC)
    val PinkPrimary = Color(0xFFF06292)
    val PurpleText = Color(0xFF880E4F)

    Scaffold(
        containerColor = PinkBackground, //background utama
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.menu_sesi),
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp), // Padding kiri-kanan
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.daftarSesi) { sesi ->
                CardSesi(sesi = sesi, onEditClick = { onEditSesi(sesi.id_sesi) })
            }
        }
    }
}

@Composable
fun CardSesi(sesi: Sesi, onEditClick: () -> Unit) {

    val WhiteCard = Color(0xFFFFFFFF)
    val PinkPrimary = Color(0xFFF06292)
    val PurpleText = Color(0xFF880E4F)
    val GreyText = Color(0xFF757575)
    val MaroonText = Color(0xFF800000)

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(20.dp), // Sudut membulat
        colors = CardDefaults.cardColors(containerColor = WhiteCard)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp) // Padding dalam kartu
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Nama Sesi
                Text(
                    text = sesi.nama_sesi,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = PurpleText
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Jam
                Text(
                    text = "Jam: ${sesi.jam_operasional}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaroonText
                )

                // Instruktur
                Text(
                    text = "Instruktur: ${sesi.nama_instruktur}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = PinkPrimary // Nama instruktur
                )
            }

            // Button Edit
            IconButton(
                onClick = onEditClick,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = PinkPrimary // Icon
                )
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit Sesi",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}