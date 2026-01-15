<?php
// 1. SET TIMEZONE WIB
date_default_timezone_set('Asia/Jakarta');

header("Content-Type: application/json; charset=UTF-8");
include '../config/database.php';

// Cek Koneksi
if (!$kon) {
    echo json_encode(["status" => "error", "message" => "Koneksi Database Gagal"]);
    exit();
}

$id_p_raw = $_POST['id_pengunjung'] ?? '';
$id_s_raw = $_POST['id_sesi'] ?? '';

if (empty($id_p_raw) || empty($id_s_raw)) {
    echo json_encode(["status" => "error", "message" => "ID kosong"]);
    exit();
}

$id_p = mysqli_real_escape_string($kon, $id_p_raw);
$id_s = mysqli_real_escape_string($kon, $id_s_raw);

// PENCARIAN POLA JAM (VALIDASI WAKTU)
$querySesi = "SELECT nama_sesi, jam_operasional FROM Sesi WHERE id_sesi = '$id_s'";
$resSesi = mysqli_query($kon, $querySesi);
$dataSesi = mysqli_fetch_assoc($resSesi);

if ($dataSesi) {
    $nama_sesi = $dataSesi['nama_sesi'];
    $jam_ops   = $dataSesi['jam_operasional']; 
    
    // Cari pola jam Angka:Angka (misal 08:00, 10:00)
    preg_match_all('/[0-9]{1,2}:[0-9]{2}/', $jam_ops, $matches);

    if (!empty($matches[0])) {
        // Ambil jam paling terakhir sebagai jam selesai
        $jam_selesai_str = end($matches[0]);
        $jam_selesai_str = date('H:i', strtotime($jam_selesai_str));
        $jam_sekarang    = date('H:i');

        // Jika sekarang lebih besar dari jam selesai -> TOLAK
        if ($jam_sekarang > $jam_selesai_str) {
            echo json_encode([
                "status" => "error", 
                "message" => "Sesi habis! Sekarang jam $jam_sekarang, sesi berakhir $jam_selesai_str."
            ]);
            exit();
        }
    }
} else {
    echo json_encode(["status" => "error", "message" => "Sesi tidak ditemukan"]);
    exit();
}

//  CEK DUPLIKAT 
$today = date('Y-m-d');
$queryCek = "SELECT id_checkin FROM Checkin 
             WHERE id_pengunjung = '$id_p' 
             AND id_sesi = '$id_s' 
             AND DATE(tanggal_checkin) = '$today'"; 

$cek = mysqli_query($kon, $queryCek);

if (mysqli_num_rows($cek) > 0) {
    echo json_encode(["status" => "error", "message" => "Sudah check-in di sesi ini hari ini"]);
    exit();
}

// UPDATE KUOTA & INSERT
$res = mysqli_query($kon, "SELECT tipe_pengunjung, kuota_sisa FROM Pengunjung WHERE id_pengunjung = '$id_p'");
$data = mysqli_fetch_assoc($res);

if (!$data) {
    echo json_encode(["status" => "error", "message" => "Pengunjung tidak ditemukan"]);
    exit();
}

if ($data['tipe_pengunjung'] == 'Member' && $data['kuota_sisa'] <= 0) {
    echo json_encode(["status" => "error", "message" => "Kuota Member Habis!"]);
    exit();
}

$update = ($data['tipe_pengunjung'] == 'Member') 
    ? "UPDATE Pengunjung SET kuota_sisa = kuota_sisa - 1, total_kunjungan = total_kunjungan + 1 WHERE id_pengunjung = '$id_p'"
    : "UPDATE Pengunjung SET total_kunjungan = total_kunjungan + 1 WHERE id_pengunjung = '$id_p'";

if (mysqli_query($kon, $update)) {
    $tipe = $data['tipe_pengunjung'];
    // Insert tanpa menyebut kolom waktu (biarkan database isi otomatis DEFAULT TIMESTAMP)
    // Atau jika database kamu tidak auto-fill, kita bisa pakai NOW()
    $insert = mysqli_query($kon, "INSERT INTO Checkin (id_pengunjung, id_sesi, tipe_saat_checkin) VALUES ('$id_p', '$id_s', '$tipe')");
    
    if ($insert) {
        echo json_encode(["status" => "success", "message" => "Check-in Berhasil"]);
    } else {
        echo json_encode(["status" => "error", "message" => "Gagal simpan riwayat"]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "Gagal Update Data"]);
}
?>