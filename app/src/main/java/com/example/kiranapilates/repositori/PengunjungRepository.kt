package com.example.kiranapilates.repositori

import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.PengunjungResponse

interface PengunjungRepository {
    suspend fun getPengunjung(token: String): PengunjungResponse
}

class NetworkPengunjungRepository(private val kiranaApiService: KiranaApiService) : PengunjungRepository {
    override suspend fun getPengunjung(token: String): PengunjungResponse =
        kiranaApiService.getAllPengunjung(token)
}