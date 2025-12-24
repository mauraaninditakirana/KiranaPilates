package com.example.kiranapilates.modeldata
import kotlinx.serialization.Serializable

@Serializable
data class Pengunjung(
    val id_pengunjung: String,
    val nama_lengkap: String,
    val no_hp: String,
    val tipe_pengunjung: String,
    val kuota_sisa: String,
    val total_kunjungan: String
)

@Serializable
data class PengunjungResponse(
    val status: String,
    val data: List<Pengunjung>
)