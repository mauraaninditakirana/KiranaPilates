package com.example.kiranapilates.repositori

import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.BasicResponse

interface CheckinRepository {
    // Fungsi untuk Halaman 10 (Submit Check-in)
    suspend fun insertCheckin(
        token: String,
        idPengunjung: String,
        idSesi: String
    ): BasicResponse
}

class NetworkCheckinRepository(private val kiranaApiService: KiranaApiService) : CheckinRepository {
    override suspend fun insertCheckin(token: String, idPengunjung: String, idSesi: String): BasicResponse =
        kiranaApiService.submitCheckin(token, idPengunjung, idSesi)
}