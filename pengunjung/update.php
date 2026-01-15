<?php
include '../config/database.php';
include '../utils/jwt_helper.php';

// 1. SET HEADER & METHOD CHECK
header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    echo json_encode(["status" => "error", "message" => "Metode harus POST"]);
    exit();
}

// 2. SECURITY CHECK (TOKEN)
$headers = getallheaders();
$authHeader = $headers['Authorization'] ?? $_SERVER['HTTP_AUTHORIZATION'] ?? '';

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

// 3. CAPTURE & AMANKAN DATA (Sanitasi)
$id_p   = isset($_POST['id_pengunjung']) ? mysqli_real_escape_string($kon, $_POST['id_pengunjung']) : '';
$nama   = isset($_POST['nama_lengkap']) ? mysqli_real_escape_string($kon, $_POST['nama_lengkap']) : '';
$hp     = isset($_POST['no_hp']) ? mysqli_real_escape_string($kon, $_POST['no_hp']) : '';
$tipe   = isset($_POST['tipe_pengunjung']) ? mysqli_real_escape_string($kon, $_POST['tipe_pengunjung']) : '';
$tambah_paket = isset($_POST['tambah_paket']) ? mysqli_real_escape_string($kon, $_POST['tambah_paket']) : '0';

// Validasi Input
if (empty($id_p) || empty($nama) || empty($hp) || empty($tipe)) {
    http_response_code(400);
    echo json_encode(["status" => "error", "message" => "Semua data wajib diisi"]);
    exit();
}

// 4. [LOGIKA BARU] CEK NOMOR HP KEMBAR (KECUALI DIRI SENDIRI)
// Cari nomor HP yang sama, TAPI ID-nya BUKAN ID orang yang sedang diedit ini
$query_cek_hp = "SELECT id_pengunjung FROM Pengunjung 
                 WHERE no_hp = '$hp' 
                 AND id_pengunjung != '$id_p'"; 

$cek_hp = mysqli_query($kon, $query_cek_hp);

if (mysqli_num_rows($cek_hp) > 0) {
    // Jika ketemu, berarti nomor itu milik orang lain
    echo json_encode(["status" => "error", "message" => "Gagal: Nomor HP ini sudah dipakai orang lain!"]);
    exit();
}


// 5. CHECK DATABASE EXISTENCE (Data Lama)
$resOld = mysqli_query($kon, "SELECT tipe_pengunjung, kuota_sisa FROM Pengunjung WHERE id_pengunjung = '$id_p'");
$dataOld = mysqli_fetch_assoc($resOld);

if (!$dataOld) {
    http_response_code(404);
    echo json_encode(["status" => "error", "message" => "Pengunjung tidak ditemukan"]);
    exit();
}

// 6. LOGIC: QUOTA & TYPE
$kuota_baru = $dataOld['kuota_sisa'];

// Reset kuota jika tipe berubah
if ($tipe !== $dataOld['tipe_pengunjung']) {
    $kuota_baru = ($tipe === 'Member') ? 10 : 0;
}

// Tambah paket jika dicentang dan tipe Member
if ($tambah_paket === '1' && $tipe === 'Member') {
    $kuota_baru += 10;
}

// 7. EXECUTE UPDATE
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