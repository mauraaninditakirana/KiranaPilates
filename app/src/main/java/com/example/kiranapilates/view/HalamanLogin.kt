package com.example.kiranapilates.view

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    val context = LocalContext.current
    val uiState = viewModel.loginUiState

    val PinkBackground = Color(0xFFFCE4EC)
    val PinkPrimary = Color(0xFFF06292)
    val PinkBorder = Color(0xFFF48FB1)
    val PurpleText = Color(0xFF880E4F)
    val WhiteContainer = Color(0xFFFFFFFF)

    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
            Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
            onLoginSuccess(uiState.token)
            viewModel.resetState()
        }
    }
    //container utama
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PinkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        // Logo
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(WhiteContainer),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.kirana_logo),
                contentDescription = "Logo Kirana Pilates",
                modifier = Modifier.fillMaxSize(), // Gambar memenuhi lingkaran
                contentScale = ContentScale.Crop // Memastikan gambar terpotong rapi mengisi lingkaran
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

        // INPUT USERNAME
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
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null, tint = PinkPrimary)
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        // TOMBOL LOGIN
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