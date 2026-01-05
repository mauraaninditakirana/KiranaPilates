<?php
include '../config/database.php'; // Sesuaikan path config kamu

// Ambil ID dari parameter URL yang dikirim Android
$id = isset($_GET['id_pengunjung']) ? $_GET['id_pengunjung'] : null;

if ($id) {
    $query = "SELECT * FROM Pengunjung WHERE id_pengunjung = '$id'";
    $result = mysqli_query($kon, $query);
    $row = mysqli_fetch_assoc($result);

    if ($row) {
        // PENTING: Bungkus $row dalam kurung siku [] agar menjadi List
        echo json_encode(["status" => "success", "data" => [$row]]);
    } else {
        echo json_encode(["status" => "error", "message" => "Data tidak ditemukan", "data" => []]);
    }
} else {
    echo json_encode(["status" => "error", "message" => "ID tidak disertakan", "data" => []]);
}
?>