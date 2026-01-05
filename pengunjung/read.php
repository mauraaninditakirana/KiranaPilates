<?php
include '../config/database.php';

$query = "SELECT * FROM Pengunjung ORDER BY created_at DESC";
$result = mysqli_query($kon, $query);
$data = [];

while ($row = mysqli_fetch_assoc($result)) {
    $data[] = $row;
}

echo json_encode(["status" => "success", "data" => $data]);
?>