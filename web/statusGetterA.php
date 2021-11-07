<?php
// $servername = "<hostname>";
// $user = "<user name>";
// $password = "<password>";
// $database = "<database name>";
// $conn = mysqli_connect($servername,$user,$password,$database);

require '../vendor/autoload.php';
require_once 'connection.php';
require_once 'credentials.php';
require_once 'response.php';


$inpEmailList = $_POST['emails'];

$query = "SELECT * FROM car_location WHERE `email` in (";

foreach($inpEmailList as $email){
    $query .= "'$email',";
}
$query = rtrim($query,',');
$query .= ")";

$sql =  mysqli_query($conn, $query);

$rows = array();
while($r = mysqli_fetch_assoc($sql)) {
  $rows[] = $r;
}
echo json_encode($rows);

$arr = array();

$out_arr = array("resposnse"=>"stat_online",
"code" => 500,
"data"=>null,
);

$out_arr["data"] = $rows;

echo json_encode($out_arr);

    
    
    
    

?>
