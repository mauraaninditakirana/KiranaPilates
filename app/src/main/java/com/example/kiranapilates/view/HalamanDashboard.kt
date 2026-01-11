package com.example.kiranapilates.view

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    // PALET WARNA TEMA
    val PinkBackground = Color(0xFFFCE4EC)
    val PinkPrimary = Color(0xFFF06292)
    val PurpleText = Color(0xFF880E4F)
    val WhiteCard = Color(0xFFFFFFFF)

    Scaffold(
        // Background Scaffold
        containerColor = PinkBackground,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.dashboard_title),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PinkBackground
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PurpleText // Header
                )
            )
        },
        // TOMBOL LOGOUT
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                    onLogout()
                },
                containerColor = PinkPrimary,
                contentColor = Color.White,   // Icon
                shape = RoundedCornerShape(50.dp) // Bulat
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp) // Jarak antar kartu
        ) {

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
            .height(110.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(6.dp), // Bayangan
        shape = RoundedCornerShape(24.dp), // Sudut membulat
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp), // Padding dalam kartu
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = iconColor
            )

            Spacer(modifier = Modifier.width(24.dp))


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