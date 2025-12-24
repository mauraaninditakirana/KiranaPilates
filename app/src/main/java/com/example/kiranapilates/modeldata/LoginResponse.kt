package com.example.kiranapilates.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val status: String,
    val message: String? = null,
    val data: AdminData? = null
)

@Serializable
data class AdminData(
    val id_admin: String,
    val username: String,
    val token: String
)