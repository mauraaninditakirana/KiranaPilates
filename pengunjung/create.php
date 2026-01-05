<?php
include '../config/database.php';

$nama = $_POST['nama_lengkap'] ?? '';
$hp   = $_POST['no_hp'] ?? '';
$tipe = $_POST['tipe_pengunjung'] ?? ''; 

$kuota = ($tipe == 'Member') ? 10 : 0; 

$query = "INSERT INTO Pengunjung (nama_lengkap, no_hp, tipe_pengunjung, kuota_sisa) 
          VALUES ('$nama', '$hp', '$tipe', '$kuota')";

if (mysqli_query($kon, $query)) {
    echo json_encode(["status" => "success", "message" => "Berhasil Simpan"]);
} else {
    echo json_encode(["status" => "error", "message" => mysqli_error($kon)]);
}
?>