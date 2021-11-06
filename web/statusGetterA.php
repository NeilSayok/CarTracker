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
$row = mysqli_fetch_array($sql);

foreach($row as $key => $value){
   echo $key.": ".$value."<br>";
}


// echo $row['log_stat'];

// $emailarr = explode('#!!#',$inpEmailList);


// $arr = array();

$out_arr = array("resposnse"=>"stat_online",
"code" => 500,
"data"=>json_encode($row),
);



// foreach($emailarr as $email){
//     $query = "SELECT `log_stat` FROM car_location WHERE `email` = '".$email."'";
//     $sql =  mysqli_query($conn, $query);
//     $row = mysqli_fetch_array($sql);
    
//     array_push($arr,$row['log_stat']);
    
// }

echo json_encode($out_arr);
    
    
    
    

?>
