<?php
include '../config/database.php';

$method = $_SERVER['REQUEST_METHOD'];

if ($method !== 'POST') {
    http_response_code(405);
    echo json_encode(["status" => "error", "message" => "Metode harus POST"]);
    exit();
}

// Ambil token dari Header Authorization
$headers = getallheaders();
$token = $headers['Authorization'] ?? '';

if (!empty($token)) {
    // Hapus token dari database agar tidak bisa digunakan lagi
    $query = "DELETE FROM Token WHERE token = '$token'";
    if (mysqli_query($kon, $query)) {
        http_response_code(200);
        echo json_encode(["status" => "success", "message" => "Logout berhasil, token telah dihapus"]);
    } else {
        http_response_code(500);
        echo json_encode(["status" => "error", "message" => "Gagal menghapus sesi"]);
    }
} else {
    http_response_code(400);
    echo json_encode(["status" => "error", "message" => "Token tidak ditemukan"]);
}
?>