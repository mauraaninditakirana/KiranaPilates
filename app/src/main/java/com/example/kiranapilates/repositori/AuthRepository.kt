package com.example.kiranapilates.repositori

import com.example.kiranapilates.apiservice.KiranaApiService
import com.example.kiranapilates.modeldata.LoginResponse

interface AuthRepository {
    suspend fun login(username: String, password: String): LoginResponse
    suspend fun logout(token: String): LoginResponse
}

class OfflineAuthRepository(private val kiranaApiService: KiranaApiService) : AuthRepository {
    override suspend fun login(username: String, password: String): LoginResponse =
        kiranaApiService.login(username, password)

    override suspend fun logout(token: String): LoginResponse =
        kiranaApiService.logout(token)
}