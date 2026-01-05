<?php
include '../config/database.php';

header('Content-Type: application/json');

$id = $_GET['id_sesi'] ?? '';

if (empty($id)) {
    echo json_encode(["status" => "error", "message" => "ID Sesi tidak boleh kosong"]);
    exit();
}

$query = "SELECT * FROM Sesi WHERE id_sesi = '$id'";
$result = mysqli_query($kon, $query);

if ($result && mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);
    // Masukkan data ke array response agar formatnya sama dengan Pengunjung
    echo json_encode([
        "status" => "success", 
        "data" => [$row] 
    ]);
} else {
    echo json_encode(["status" => "error", "message" => "Data sesi tidak ditemukan"]);
}
?>