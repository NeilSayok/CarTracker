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

// $inp_arr = json_decode($inpEmailList,true);

$query = "SELECT * FROM car_location WHERE `email` in (";

foreach($inpEmailList as $email){
    $query .= "'$email',";
}
$query = rtrim($query,',');
$query .= ")";


// $query = "SELECT `email`,`log_stat` FROM car_location WHERE `email` = '".$inpEmailList."'";


$sql =  mysqli_query($conn, $query);

// $rows = array();
// while($r  = mysqli_fetch_array($sql)){
//     $rows[] = $r;
// }
// echo json_encode($row);

$rows = array();
while($r = mysqli_fetch_assoc($sql)) {
  $rows[] = $r;
}
echo json_encode($rows);


// foreach($row as $key => $value){
//    echo $key.": ".$value."<br>";
// }


// echo $row['log_stat'];

// $emailarr = explode('#!!#',$inpEmailList);


$arr = array();

$out_arr = array("resposnse"=>"stat_online",
"code" => 500,
"data"=>null,
);



// foreach($row as $r){
//     echo json_encode($r);
//     array_push($arr,$r);
    
// }

$out_arr["data"] = $arr;

//echo json_encode($out_arr);
    
    
    
    

?>
