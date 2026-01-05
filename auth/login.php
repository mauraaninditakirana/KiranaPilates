<?php
include '../config/database.php';
include '../utils/jwt_helper.php'; 

$username = $_POST['username'] ?? '';
$password = md5($_POST['password'] ?? '');

$query = "SELECT * FROM Admin WHERE username = '$username' AND password = '$password'";
$result = mysqli_query($kon, $query);

if (mysqli_num_rows($result) > 0) {
    $admin = mysqli_fetch_assoc($result);
    $id_admin = $admin['id_admin'];
    
    // Pakai fungsi dari jwt_helper.php
    $token = generateToken($id_admin); 
    
    mysqli_query($kon, "INSERT INTO Token (id_admin, token) VALUES ('$id_admin', '$token')");

    echo json_encode([
        "status" => "success",
        "data" => ["id_admin" => $id_admin, "username" => $admin['username'], "token" => $token]
    ]);
} else {
    echo json_encode(["status" => "error", "message" => "Login Gagal"]);
}
?>