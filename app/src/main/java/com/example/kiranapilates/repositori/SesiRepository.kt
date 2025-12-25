package com.example.kiranapilates.repositori
import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.BasicResponse
import com.example.kiranapilates.modeldata.SesiResponse

interface SesiRepository {
    suspend fun getSesi(token: String): SesiResponse
    suspend fun updateSesi(token: String, id: String, jam: String, instruktur: String): BasicResponse
}

class NetworkSesiRepository(private val kiranaApiService: KiranaApiService) : SesiRepository {
    override suspend fun getSesi(token: String): SesiResponse = kiranaApiService.getAllSesi(token)

    override suspend fun updateSesi(token: String, id: String, jam: String, instruktur: String): BasicResponse =
        kiranaApiService.updateSesi(token, id, jam, instruktur)
}