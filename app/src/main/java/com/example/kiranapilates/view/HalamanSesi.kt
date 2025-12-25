package com.example.kiranapilates.view

import androidx.compose.runtime.Composable
import com.example.kiranapilates.viewmodel.SesiViewModel

@Composable
fun HalamanSesi(
    viewModel: SesiViewModel,
    onEditClick: (String) -> Unit, // Kirim ID Sesi ke Hal 8
    onBack: () -> Unit
) {}