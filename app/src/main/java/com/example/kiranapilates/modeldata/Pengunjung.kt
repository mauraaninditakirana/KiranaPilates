package com.example.kiranapilates.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Pengunjung(
    val id_pengunjung: Int,
    val nama_lengkap: String,
    val no_hp: String,
    val tipe_pengunjung: String, // Member atau Guest
    val kuota_sisa: Int,
    val total_kunjungan: Int,
    val created_at: String? = null
)

@Serializable
data class PengunjungResponse(
    val status: String,
    val message: String? = null,
    val data: List<Pengunjung>? = null
)