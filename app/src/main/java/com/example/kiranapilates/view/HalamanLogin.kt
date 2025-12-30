package com.example.kiranapilates.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kiranapilates.R
import com.example.kiranapilates.viewmodel.LoginUiState
import com.example.kiranapilates.viewmodel.LoginViewModel
import com.example.kiranapilates.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanLogin(
    onLoginSuccess: (String) -> Unit,
    viewModel: LoginViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.loginUiState

    // --- PALET WARNA TEMA PINK PASTEL ---
    val PinkBackground = Color(0xFFFCE4EC)
    val PinkPrimary = Color(0xFFF06292)
    val PinkBorder = Color(0xFFF48FB1)
    val PurpleText = Color(0xFF880E4F)
    val WhiteContainer = Color(0xFFFFFFFF)

    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
            onLoginSuccess(uiState.token)
            viewModel.resetState()
        }
    }

    // Container Utama
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PinkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Spacer pendorong
        Spacer(modifier = Modifier.weight(1f))

        // --- ILUSTRASI LOGO ---
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(WhiteContainer),
            contentAlignment = Alignment.Center
        ) {
            // SAYA GANTI JADI 'SPA' (Bunga Lotus)
            // Ini simbol universal untuk Pilates/Yoga/Meditasi yang cantik & estetik
            Icon(
                imageVector = Icons.Default.Spa,
                contentDescription = null,
                modifier = Modifier.size(70.dp),
                tint = PinkPrimary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // HEADER TEKS
        Text(
            text = "Selamat Datang Admin",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = PurpleText,
                letterSpacing = 1.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Silahkan Login untuk mulai bekerja!",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = PinkPrimary,
                fontWeight = FontWeight.Medium
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- INPUT USERNAME (Icon Orang) ---
        OutlinedTextField(
            value = viewModel.usernameInput,
            onValueChange = { viewModel.usernameInput = it },
            label = { Text(stringResource(R.string.username_hint), color = PinkPrimary) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = WhiteContainer,
                unfocusedContainerColor = WhiteContainer,
                focusedBorderColor = PinkPrimary,
                unfocusedBorderColor = PinkBorder,
                cursorColor = PinkPrimary,
                focusedLabelColor = PinkPrimary,
            ),
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null, tint = PinkPrimary)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- INPUT PASSWORD (ADA ICON GEMBOK) ---
        OutlinedTextField(
            value = viewModel.passwordInput,
            onValueChange = { viewModel.passwordInput = it },
            label = { Text(stringResource(R.string.password_hint), color = PinkPrimary) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = WhiteContainer,
                unfocusedContainerColor = WhiteContainer,
                focusedBorderColor = PinkPrimary,
                unfocusedBorderColor = PinkBorder,
                cursorColor = PinkPrimary,
                focusedLabelColor = PinkPrimary,
            ),
            singleLine = true,
            // TAMBAHAN ICON KUNCI (LOCK) DISINI
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, tint = PinkPrimary)
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // --- TOMBOL LOGIN ---
        Button(
            onClick = { viewModel.login() },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            enabled = uiState !is LoginUiState.Loading,
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PinkPrimary,
                contentColor = Color.White,
                disabledContainerColor = PinkBorder.copy(alpha = 0.5f)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            if (uiState is LoginUiState.Loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = stringResource(R.string.login_button),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        if (uiState is LoginUiState.Error) {
            Text(
                text = uiState.message,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1.5f))
    }
}