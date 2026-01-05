<?php
include '../config/database.php';
header('Content-Type: application/json');

// Tangkap ID Sesi dan Tanggal yang dikirim dari HP
$id_sesi = $_GET['id_sesi'] ?? '';
$tanggal = $_GET['tanggal'] ?? '';

// Validasi: Kalau kosong, tolak
if (empty($id_sesi) || empty($tanggal)) {
    echo json_encode(["status" => "error", "message" => "Parameter Sesi dan Tanggal wajib diisi"]);
    exit();
}

// Query JOIN: Mengambil data checkin + Nama Pengunjung + Nama Sesi
// FIX: Ganti = dengan LIKE untuk matching tanggal
$query = "SELECT 
            c.id_checkin, 
            c.id_pengunjung,       
            c.id_sesi,              
            c.tanggal_checkin, 
            c.tipe_saat_checkin,    
            p.nama_lengkap, 
            s.nama_sesi, 
            s.jam_operasional
          FROM Checkin c
          JOIN Pengunjung p ON c.id_pengunjung = p.id_pengunjung
          JOIN Sesi s ON c.id_sesi = s.id_sesi
          WHERE c.id_sesi = '$id_sesi' 
          AND c.tanggal_checkin LIKE '$tanggal%'
          ORDER BY c.id_checkin DESC";

$result = mysqli_query($kon, $query);

$data = [];
if ($result) {
    while ($row = mysqli_fetch_assoc($result)) {
        $data[] = $row;
    }
}

// Kirim balik ke HP dalam format JSON
echo json_encode([
    "status" => "success",
    "message" => "Data berhasil diambil",
    "data" => $data
]);
?>