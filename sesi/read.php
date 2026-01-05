<?php
include '../config/database.php';

header('Content-Type: application/json');

$query = "SELECT * FROM Sesi ORDER BY id_sesi ASC";
$result = mysqli_query($kon, $query);
$data = [];

while ($row = mysqli_fetch_assoc($result)) {
    $data[] = $row;
}

echo json_encode(["status" => "success", "data" => $data]);
?>