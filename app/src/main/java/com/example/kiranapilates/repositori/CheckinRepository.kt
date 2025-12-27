package com.example.kiranapilates.repositori

import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.CheckinResponse

// REPOSITORY CHECKIN
interface CheckinRepository {
    suspend fun createCheckin(idPengunjung: Int, idSesi: Int): CheckinResponse
}

class OfflineCheckinRepository(private val kiranaApiService: KiranaApiService) : CheckinRepository {
    override suspend fun createCheckin(idPengunjung: Int, idSesi: Int): CheckinResponse =
        kiranaApiService.createCheckin(idPengunjung, idSesi)
}