<?php

include '../config/database.php';

$id_p = $_POST['id_pengunjung'] ?? '';

$id_s = $_POST['id_sesi'] ?? '';

// Ambil data terbaru pengunjung

$res = mysqli_query($kon, "SELECT tipe_pengunjung, kuota_sisa FROM Pengunjung WHERE id_pengunjung = '$id_p'");

$data = mysqli_fetch_assoc($res);

if (!$data) {

    echo json_encode(["status" => "error", "message" => "Data tidak ditemukan"]);

    exit();

}

// Cek kuota Member (REQ-CHK-104)

if ($data['tipe_pengunjung'] == 'Member' && $data['kuota_sisa'] <= 0) {

    echo json_encode(["status" => "error", "message" => "Kuota Member Habis!"]);

    exit();

}

// Set logika update (REQ-CCT-101)

$update = ($data['tipe_pengunjung'] == 'Member') 

    ? "UPDATE Pengunjung SET kuota_sisa = kuota_sisa - 1, total_kunjungan = total_kunjungan + 1 WHERE id_pengunjung = '$id_p'"

    : "UPDATE Pengunjung SET total_kunjungan = total_kunjungan + 1 WHERE id_pengunjung = '$id_p'";

if (mysqli_query($kon, $update)) {

    // Simpan log transaksi ke tabel Checkin

    $tipe = $data['tipe_pengunjung'];

    mysqli_query($kon, "INSERT INTO Checkin (id_pengunjung, id_sesi, tipe_saat_checkin) VALUES ('$id_p', '$id_s', '$tipe')");

    

    echo json_encode(["status" => "success", "message" => "Check-in Berhasil"]);

} else {

    echo json_encode(["status" => "error", "message" => "Gagal Update Data"]);

}

?>

