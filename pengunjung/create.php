<?php
// backend/pengunjung/create.php

header("Content-Type: application/json; charset=UTF-8");
include '../config/database.php';
// Cek koneksi
if (!$kon) {
    echo json_encode(["status" => "error", "message" => "Koneksi Database Gagal"]);
    exit();
}
// 1. AMBIL & AMANKAN INPUT (Sanitasi)
// Menggunakan mysqli_real_escape_string agar karakter ' aman
$nama = isset($_POST['nama_lengkap']) ? mysqli_real_escape_string($kon, $_POST['nama_lengkap']) : '';
$hp   = isset($_POST['no_hp']) ? mysqli_real_escape_string($kon, $_POST['no_hp']) : '';
$tipe = isset($_POST['tipe_pengunjung']) ? mysqli_real_escape_string($kon, $_POST['tipe_pengunjung']) : ''; 

// Validasi input kosong
if (empty($nama) || empty($hp) || empty($tipe)) {
    echo json_encode(["status" => "error", "message" => "Nama, HP, dan Tipe wajib diisi"]);
    exit();
}

// 2. [LOGIKA BARU] CEK NOMOR HP KEMBAR
// Cari apakah nomor HP ini sudah ada di database?
$cek_hp = mysqli_query($kon, "SELECT id_pengunjung FROM Pengunjung WHERE no_hp = '$hp'");

if (mysqli_num_rows($cek_hp) > 0) {
    // Jika ketemu (jumlah baris > 0), berarti sudah terdaftar
    echo json_encode(["status" => "error", "message" => "Gagal: Nomor HP sudah terdaftar!"]);
    exit(); // Stop proses disini
}

// 3. HITUNG KUOTA AWAL
$kuota = ($tipe == 'Member') ? 10 : 0; 

// 4. SIMPAN DATA
$query = "INSERT INTO Pengunjung (nama_lengkap, no_hp, tipe_pengunjung, kuota_sisa) 
          VALUES ('$nama', '$hp', '$tipe', '$kuota')";

if (mysqli_query($kon, $query)) {
    echo json_encode(["status" => "success", "message" => "Berhasil Simpan Data Pengunjung"]);
} else {
    echo json_encode(["status" => "error", "message" => "Database Error: " . mysqli_error($kon)]);
}
?>