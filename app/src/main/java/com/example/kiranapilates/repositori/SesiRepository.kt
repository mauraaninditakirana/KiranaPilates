package com.example.kiranapilates.repositori

import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.SesiResponse

interface SesiRepository {
    suspend fun getSesi(token: String): SesiResponse
}

class NetworkSesiRepository(private val kiranaApiService: KiranaApiService) : SesiRepository {
    override suspend fun getSesi(token: String): SesiResponse =
        kiranaApiService.getAllSesi(token)
}