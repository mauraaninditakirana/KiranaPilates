<?php
include '../config/database.php';

$id_p = $_POST['id_pengunjung'] ?? '';

// Query hapus (REQ-REG-110)
$query = "DELETE FROM Pengunjung WHERE id_pengunjung = '$id_p'";

if (mysqli_query($kon, $query)) {
    echo json_encode(["status" => "success", "message" => "Data Pengunjung Berhasil Dihapus"]);
} else {
    echo json_encode(["status" => "error", "message" => "Gagal Menghapus Data"]);
}
?>