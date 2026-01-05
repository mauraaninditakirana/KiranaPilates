<?php
// Fungsi untuk membuat token acak (sebagai pengganti library JWT jika belum install)
function generateToken($id_admin) {
    // Menggabungkan ID Admin dengan kode unik dan di-hash agar aman
    return md5($id_admin . time() . "KIRANA_SECRET_KEY");
}

// Fungsi untuk mengecek apakah token yang dikirim Android valid
function checkToken($kon, $token) {
    $query = "SELECT * FROM Token WHERE token = '$token'";
    $result = mysqli_query($kon, $query);
    return (mysqli_num_rows($result) > 0);
}
?>