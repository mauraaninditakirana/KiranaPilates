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


    // --- 2. PENGUNJUNG (Halaman 3 - 6) ---
    @GET("pengunjung/read.php")
    suspend fun getAllPengunjung(
        @Header("Authorization") token: String
    ): PengunjungResponse

    @FormUrlEncoded
    @POST("pengunjung/create.php")
    suspend fun insertPengunjung(
        @Header("Authorization") token: String,
        @Field("nama_lengkap") nama: String,
        @Field("no_hp") hp: String,
        @Field("tipe_pengunjung") tipe: String
    ): BasicResponse

    @FormUrlEncoded
    @PUT("pengunjung/update.php")
    suspend fun updatePengunjung(
        @Header("Authorization") token: String,
        @Field("id_pengunjung") id: String,
        @Field("nama_lengkap") nama: String,
        @Field("no_hp") hp: String,
        @Field("tipe_pengunjung") tipe: String
    ): BasicResponse

    @FormUrlEncoded
    @POST("pengunjung/delete.php")
    suspend fun deletePengunjung(
        @Header("Authorization") token: String,
        @Field("id_pengunjung") id: String
    ): BasicResponse

    // --- 3. SESI (Halaman 7 - 8) ---
    @GET("sesi/read.php")
    suspend fun getAllSesi(
        @Header("Authorization") token: String
    ): SesiResponse

    @FormUrlEncoded
    @POST("sesi/update.php")
    suspend fun updateSesi(
        @Header("Authorization") token: String,
        @Field("id_sesi") id: String,
        @Field("jam_operasional") jam: String,
        @Field("nama_instruktur") instruktur: String
    ): BasicResponse
}