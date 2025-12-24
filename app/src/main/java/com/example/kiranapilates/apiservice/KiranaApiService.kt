package com.example.kiranapilates.apiservice


import com.example.kiranapilates.modeldata.*
import retrofit2.http.*

interface KiranaApiService {

    // --- 1. AUTH (Halaman 1 & 2) ---
    @FormUrlEncoded
    @POST("auth/login.php")
    suspend fun loginAdmin(
        @Field("username") user: String,
        @Field("password") pass: String
    ): LoginResponse

    @POST("auth/logout.php")
    suspend fun logoutAdmin(
        @Header("Authorization") token: String
    ): BasicResponse

}