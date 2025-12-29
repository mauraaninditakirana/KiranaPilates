package com.example.kiranapilates.repositori

import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.PengunjungResponse

interface PengunjungRepository {
    suspend fun getAllPengunjung(): PengunjungResponse
    suspend fun insertPengunjung(nama: String, hp: String, tipe: String): PengunjungResponse
    suspend fun updatePengunjung(token: String, id: Int, nama: String, hp: String, tipe: String, tambahPaket: String): PengunjungResponse
    suspend fun deletePengunjung(id: Int): PengunjungResponse
    suspend fun getPengunjungById(id: Int): PengunjungResponse
}

class OfflinePengunjungRepository(private val kiranaApiService: KiranaApiService) : PengunjungRepository {
    override suspend fun getAllPengunjung(): PengunjungResponse =
        kiranaApiService.getAllPengunjung()

    override suspend fun insertPengunjung(nama: String, hp: String, tipe: String): PengunjungResponse =
        kiranaApiService.createPengunjung(nama, hp, tipe)

    override suspend fun updatePengunjung(token: String, id: Int, nama: String, hp: String, tipe: String, tambahPaket: String): PengunjungResponse =
        kiranaApiService.updatePengunjung(token, id, nama, hp, tipe, tambahPaket)

    override suspend fun deletePengunjung(id: Int): PengunjungResponse =
        kiranaApiService.deletePengunjung(id)

    override suspend fun getPengunjungById(id: Int): PengunjungResponse =
        kiranaApiService.getPengunjungById(id)
}