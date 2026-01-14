<?php
// checkin/create.php

include '../config/database.php';

// Mengambil data Input dan mengamankannya sedikit (sanitasi dasar)
// Menggunakan mysqli_real_escape_string untuk mencegah karakter aneh yang merusak query SQL
$id_p = isset($_POST['id_pengunjung']) ? mysqli_real_escape_string($kon, $_POST['id_pengunjung']) : '';
$id_s = isset($_POST['id_sesi']) ? mysqli_real_escape_string($kon, $_POST['id_sesi']) : '';

// Validasi dasar jika ID kosong
if (empty($id_p) || empty($id_s)) {
    echo json_encode(["status" => "error", "message" => "Data ID tidak lengkap"]);
    exit();
}

// ==================================================================
// [LOGIKA BARU DITAMBAHKAN DI SINI]
// Cek Double Check-in di Hari & Sesi yang Sama
// ==================================================================

// 1. Ambil tanggal hari ini dari server (format YYYY-MM-DD)
$tanggal_hari_ini = date('Y-m-d');

// 2. Buat query untuk mengecek keberadaan data
// Kita menggunakan fungsi SQL `DATE(waktu_checkin)` agar yang dibandingkan hanya tanggalnya saja, jamnya diabaikan.
$query_cek_double = "SELECT id_checkin FROM Checkin 
                     WHERE id_pengunjung = '$id_p' 
                     AND id_sesi = '$id_s' 
                     AND DATE(waktu_checkin) = '$tanggal_hari_ini'";

$res_cek_double = mysqli_query($kon, $query_cek_double);

// 3. Jika ditemukan data (jumlah baris > 0), berarti sudah pernah check-in hari ini di sesi ini.
if (mysqli_num_rows($res_cek_double) > 0) {
    // Kirim pesan error ke Android dan STOP proses selanjutnya.
    echo json_encode([
        "status" => "error", 
        "message" => "Gagal: Pengunjung ini sudah check-in pada sesi ini hari ini!"
    ]);
    exit(); // PENTING: exit() agar kode di bawahnya tidak dijalankan.
}

// ==================================================================
// [AKHIR LOGIKA BARU] - Kode di bawah ini adalah logika asli Anda
// ==================================================================


// Ambil data terbaru pengunjung
$res = mysqli_query($kon, "SELECT tipe_pengunjung, kuota_sisa FROM Pengunjung WHERE id_pengunjung = '$id_p'");
$data = mysqli_fetch_assoc($res);

if (!$data) {
    echo json_encode(["status" => "error", "message" => "Data pengunjung tidak ditemukan"]);
    exit();
}

// Cek kuota Member (REQ-CHK-104)
if ($data['tipe_pengunjung'] == 'Member' && $data['kuota_sisa'] <= 0) {
    echo json_encode(["status" => "error", "message" => "Kuota Member Habis!"]);
    exit();
}

// Set logika update (REQ-CCT-101)
// Jika member, kurangi kuota dan tambah total kunjungan. Jika guest, cuma tambah total kunjungan.
$update = ($data['tipe_pengunjung'] == 'Member') 
    ? "UPDATE Pengunjung SET kuota_sisa = kuota_sisa - 1, total_kunjungan = total_kunjungan + 1 WHERE id_pengunjung = '$id_p'"
    : "UPDATE Pengunjung SET total_kunjungan = total_kunjungan + 1 WHERE id_pengunjung = '$id_p'";

// Eksekusi update ke tabel Pengunjung dulu
if (mysqli_query($kon, $update)) {
    
    // Jika berhasil update pengunjung, baru Simpan log transaksi ke tabel Checkin
    // Catatan: Kolom 'waktu_checkin' biasanya otomatis terisi oleh database (CURRENT_TIMESTAMP)
    $tipe = $data['tipe_pengunjung'];
    $insert_checkin = mysqli_query($kon, "INSERT INTO Checkin (id_pengunjung, id_sesi, tipe_saat_checkin) VALUES ('$id_p', '$id_s', '$tipe')");
    
    if ($insert_checkin) {
        echo json_encode(["status" => "success", "message" => "Check-in Berhasil"]);
    } else {
        // Jika gagal insert checkin (tapi sudah terlanjur update kuota - kasus jarang tapi bisa terjadi)
        // Idealnya pakai TRANSACTION SQL, tapi untuk saat ini error ini cukup.
        echo json_encode(["status" => "error", "message" => "Gagal menyimpan riwayat check-in"]);
    }

} else {
    echo json_encode(["status" => "error", "message" => "Gagal Update Data Pengunjung"]);
}
?>