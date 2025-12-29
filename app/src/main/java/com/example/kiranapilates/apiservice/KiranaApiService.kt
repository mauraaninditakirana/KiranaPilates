package com.example.kiranapilates.apiservice

import com.example.kiranapilates.modeldata.*
import retrofit2.http.*

interface KiranaApiService {

    // --- AUTHENTICATION ---
    @FormUrlEncoded
    @POST("auth/login.php")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @POST("auth/logout.php")
    suspend fun logout(
        @Header("Authorization") token: String
    ): LoginResponse


    // --- PENGUNJUNG ---
    @GET("pengunjung/read.php")
    suspend fun getAllPengunjung(): PengunjungResponse

    @FormUrlEncoded
    @POST("pengunjung/create.php")
    suspend fun createPengunjung(
        @Field("nama_lengkap") nama: String,
        @Field("no_hp") hp: String,
        @Field("tipe_pengunjung") tipe: String
    ): PengunjungResponse

    // Di KiranaApiService.kt
    @FormUrlEncoded
    @POST("pengunjung/update.php") // Ubah jadi POST
    suspend fun updatePengunjung(
        @Header("Authorization") token: String,
        @Field("id_pengunjung") id: String,
        @Field("nama_lengkap") nama: String,
        @Field("no_hp") hp: String,
        @Field("tipe_pengunjung") tipe: String,
        @Field("tambah_paket") tambahPaket: String
    ): PengunjungResponse

    @FormUrlEncoded
    @POST("pengunjung/delete.php")
    suspend fun deletePengunjung(
        @Field("id_pengunjung") id: Int
    ): PengunjungResponse


    // --- SESI ---
    @GET("sesi/read.php")
    suspend fun getAllSesi(): SesiResponse

    @FormUrlEncoded
    @POST("sesi/update.php")
    suspend fun updateSesi(
        @Header("Authorization") token: String,
        @Field("id_sesi") id: Int,
        @Field("nama_sesi") nama: String,
        @Field("jam_operasional") jam: String,
        @Field("nama_instruktur") instruktur: String
    ): SesiResponse

    @GET("sesi/get_by_id.php")
    suspend fun getSesiById(
        @Query("id_sesi") id: Int
    ): SesiResponse

    // --- CHECKIN ---
    @FormUrlEncoded
    @POST("checkin/create.php")
    suspend fun createCheckin(
        @Field("id_pengunjung") idPengunjung: Int,
        @Field("id_sesi") idSesi: Int
    ): CheckinResponse

    @GET("pengunjung/get_by_id.php")
    suspend fun getPengunjungById(@Query("id_pengunjung") id: Int): PengunjungResponse
}