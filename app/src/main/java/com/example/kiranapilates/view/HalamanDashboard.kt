package com.example.kiranapilates.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kiranapilates.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanDashboard(
    onNavigateToRegistrasi: () -> Unit,
    onNavigateToSesi: () -> Unit,
    onNavigateToCheckin: () -> Unit,
    onLogout: () -> Unit
) {
    // --- PALET WARNA TEMA (Sama dengan Login) ---
    val PinkBackground = Color(0xFFFCE4EC)
    val PinkPrimary = Color(0xFFF06292) // Warna tombol/icon
    val PurpleText = Color(0xFF880E4F)  // Warna teks judul
    val WhiteCard = Color(0xFFFFFFFF)   // Warna kartu biar bersih

    Scaffold(
        // Background Scaffold disamakan agar seamless
        containerColor = PinkBackground,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.dashboard_title),
                        style = MaterialTheme.typography.headlineMedium.copy( // Font lebih besar
                            fontWeight = FontWeight.Bold,
                            color = PurpleText
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PinkBackground // Header nyatu dengan background
                )
            )
        },
        // TOMBOL LOGOUT (Dipindah ke Kanan Bawah, Bulat)
        floatingActionButton = {
            FloatingActionButton(
                onClick = onLogout,
                containerColor = PinkPrimary, // Warna Pink Fanta
                contentColor = Color.White,   // Icon Putih
                shape = RoundedCornerShape(50.dp) // Bulat penuh
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp), // Padding agak lega
            verticalArrangement = Arrangement.spacedBy(20.dp) // Jarak antar kartu lebih renggang
        ) {
            // Teks "Admin bebas..." SUDAH DIHAPUS

            // MENU KARTU
            MenuCard(
                title = stringResource(R.string.menu_registrasi),
                icon = Icons.Default.AccountBox,
                onClick = onNavigateToRegistrasi,
                iconColor = PinkPrimary,
                textColor = PurpleText,
                cardColor = WhiteCard
            )

            MenuCard(
                title = stringResource(R.string.menu_sesi),
                icon = Icons.Default.DateRange,
                onClick = onNavigateToSesi,
                iconColor = PinkPrimary,
                textColor = PurpleText,
                cardColor = WhiteCard
            )

            MenuCard(
                title = stringResource(R.string.menu_checkin),
                icon = Icons.Default.CheckCircle,
                onClick = onNavigateToCheckin,
                iconColor = PinkPrimary,
                textColor = PurpleText,
                cardColor = WhiteCard
            )
        }
    }
}

@Composable
fun MenuCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    iconColor: Color,
    textColor: Color,
    cardColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp) // Sedikit lebih tinggi biar cantik
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(6.dp), // Bayangan agak tebal biar timbul
        shape = RoundedCornerShape(24.dp), // Sudut membulat banget
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), // Padding dalam kartu
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Besar Pink
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = iconColor
            )

            Spacer(modifier = Modifier.width(24.dp))

            // Teks Judul Ungu
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = textColor
            )
        }
    }
}