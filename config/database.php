<?php
$host = "localhost";
$user = "root";
$pass = ""; 
$db   = "db_kirana_pilates"; 

$kon = mysqli_connect($host, $user, $pass, $db);

if (!$kon) {
    echo json_encode(["status" => "error", "message" => "Koneksi Gagal"]);
    exit();
}

header('Content-Type: application/json');
?>