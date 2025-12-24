package com.example.kiranapilates.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Checkin(
    val id_checkin: String? = null,
    val id_pengunjung: String,
    val nama_lengkap: String? = null,
    val id_sesi: String,
    val nama_sesi: String? = null,
    val tanggal: String? = null
)

@Serializable
data class BasicResponse(
    val status: String,
    val message: String
)