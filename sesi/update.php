<?php
include '../config/database.php';
include '../utils/jwt_helper.php';

header('Content-Type: application/json');

// Cek Token Security
$headers = getallheaders();
$token = $headers['Authorization'] ?? '';

if (empty($token) || !checkToken($kon, $token)) {
    http_response_code(401);
    echo json_encode(["status" => "error", "message" => "Token tidak valid"]);
    exit();
}

// Ambil data POST
$id_sesi = $_POST['id_sesi'] ?? '';
$nama_s  = $_POST['nama_sesi'] ?? '';
$jam     = $_POST['jam_operasional'] ?? '';
$inst    = $_POST['nama_instruktur'] ?? '';

if (empty($id_sesi)) {
    http_response_code(400);
    echo json_encode(["status" => "error", "message" => "ID Sesi wajib diisi"]);
    exit();
}

$query = "UPDATE Sesi SET nama_sesi='$nama_s', jam_operasional='$jam', nama_instruktur='$inst' WHERE id_sesi='$id_sesi'";

if (mysqli_query($kon, $query)) {
    echo json_encode(["status" => "success", "message" => "Sesi berhasil diupdate"]);
} else {
    http_response_code(500);
    echo json_encode(["status" => "error", "message" => "Gagal update database"]);
}
?>