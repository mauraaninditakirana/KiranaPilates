<?php
include '../config/database.php';
include '../utils/jwt_helper.php';

// 1. SET HEADER & METHOD CHECK
header('Content-Type: application/json');

// Pastikan metode adalah POST (Jalur paling aman untuk Android)
if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    echo json_encode(["status" => "error", "message" => "Metode harus POST"]);
    exit();
}

// 2. SECURITY CHECK (TOKEN YANG LEBIH KUAT)
$headers = getallheaders();
$authHeader = $headers['Authorization'] ?? $_SERVER['HTTP_AUTHORIZATION'] ?? '';

// Bersihkan awalan "Bearer " jika ada (Android kadang mengirimnya otomatis)
if (preg_match('/Bearer\s(\S+)/', $authHeader, $matches)) {
    $token = $matches[1];
} else {
    $token = $authHeader;
}

if (empty($token) || !checkToken($kon, $token)) {
    http_response_code(401);
    echo json_encode(["status" => "error", "message" => "Token tidak valid atau sesi habis"]);
    exit();
}

// 3. CAPTURE DATA (Gunakan $_POST agar sinkron dengan @FormUrlEncoded)
$id_p   = $_POST['id_pengunjung'] ?? '';
$nama   = $_POST['nama_lengkap'] ?? '';
$hp     = $_POST['no_hp'] ?? '';
$tipe   = $_POST['tipe_pengunjung'] ?? '';
$tambah_paket = $_POST['tambah_paket'] ?? '0';

// Validasi Input
if (empty($id_p) || empty($nama) || empty($hp) || empty($tipe)) {
    http_response_code(400);
    echo json_encode(["status" => "error", "message" => "Semua data wajib diisi"]);
    exit();
}

// 4. CHECK DATABASE EXISTENCE
$resOld = mysqli_query($kon, "SELECT tipe_pengunjung, kuota_sisa FROM Pengunjung WHERE id_pengunjung = '$id_p'");
$dataOld = mysqli_fetch_assoc($resOld);

if (!$dataOld) {
    http_response_code(404);
    echo json_encode(["status" => "error", "message" => "Pengunjung tidak ditemukan"]);
    exit();
}

// 5. LOGIC: QUOTA & TYPE
$kuota_baru = $dataOld['kuota_sisa'];

// Reset kuota jika tipe berubah
if ($tipe !== $dataOld['tipe_pengunjung']) {
    $kuota_baru = ($tipe === 'Member') ? 10 : 0;
}

// Tambah paket jika dicentang dan tipe Member
if ($tambah_paket === '1' && $tipe === 'Member') {
    $kuota_baru += 10;
}

// 6. EXECUTE UPDATE
$query = "UPDATE Pengunjung SET 
          nama_lengkap = '$nama', 
          no_hp = '$hp', 
          tipe_pengunjung = '$tipe', 
          kuota_sisa = '$kuota_baru' 
          WHERE id_pengunjung = '$id_p'";

if (mysqli_query($kon, $query)) {
    http_response_code(200);
    echo json_encode(["status" => "success", "message" => "Data berhasil diperbarui"]);
} else {
    http_response_code(500);
    echo json_encode(["status" => "error", "message" => "Database Error: " . mysqli_error($kon)]);
}
?>