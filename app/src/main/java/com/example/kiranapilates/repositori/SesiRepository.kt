package com.example.kiranapilates.repositori

import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.SesiResponse

// REPOSITORY SESI
interface SesiRepository {
    suspend fun getSesi(): SesiResponse
    suspend fun updateSesi(token: String, id: Int, nama: String, jam: String, instruktur: String): SesiResponse
    suspend fun getSesiById(id: Int): SesiResponse
}

class OfflineSesiRepository(private val kiranaApiService: KiranaApiService) : SesiRepository {
    override suspend fun getSesi(): SesiResponse = kiranaApiService.getAllSesi()
    override suspend fun updateSesi(token: String, id: Int, nama: String, jam: String, instruktur: String): SesiResponse =
        kiranaApiService.updateSesi(token, id, nama, jam, instruktur)
    override suspend fun getSesiById(id: Int): SesiResponse =
        kiranaApiService.getSesiById(id)
}