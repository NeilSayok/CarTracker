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

$out_arr = array("resposnse"=>"stat_online",
"code" => 500,
"data"=>null,
);

$sql =  mysqli_query($conn, $query);

if(empty($sql)){
    $out_arr["response"] = "stat_offline";
    $out_arr["code"] = 501;
    $out_arr["data"] = null;
}
else{
    $rows = array();
    while($r = mysqli_fetch_assoc($sql)) {
        $rows[] = $r;
    }
    $out_arr["response"] = "stat_online";
    $out_arr["code"] = 500;
    $out_arr["data"] = json_encode($rows);
}


echo json_encode($out_arr);

    
    
    
    

?>
