package com.example.kiranapilates.repositori

import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.CheckinResponse
import com.example.kiranapilates.modeldata.RiwayatCheckinResponse

// REPOSITORY CHECKIN
interface CheckinRepository {
    suspend fun createCheckin(idPengunjung: Int, idSesi: Int): CheckinResponse
    suspend fun getRiwayatCheckin(idSesi: Int, tanggal: String): RiwayatCheckinResponse
}

class OfflineCheckinRepository(
    private val kiranaApiService: KiranaApiService
) : CheckinRepository {

    override suspend fun createCheckin(idPengunjung: Int, idSesi: Int): CheckinResponse {
        return kiranaApiService.createCheckin(idPengunjung, idSesi)
    }

    override suspend fun getRiwayatCheckin(idSesi: Int, tanggal: String): RiwayatCheckinResponse {
        return kiranaApiService.getRiwayatCheckin(idSesi, tanggal)
    }
}