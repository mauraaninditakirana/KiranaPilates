package com.example.kiranapilates.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Sesi(
    val id_sesi: Int,
    val nama_sesi: String,
    val jam_operasional: String,
    val nama_instruktur: String,
    val updated_at: String? = null
)

@Serializable
data class SesiResponse(
    val status: String,
    val message: String? = null,
    val data: List<Sesi>? = null
)
