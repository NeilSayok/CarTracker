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

echo $inpEmailList;


// $inp_arr = json_decode($inpEmailList,true);

// foreach($inp_arr as $email){
//     echo $email;
// }

// $query = "SELECT `email`,`log_stat` FROM car_location WHERE `email` = '".$inpEmailList."'";

/*$sql =  mysqli_query($conn, $query);
$row = mysqli_fetch_array($sql);
echo $row['log_stat'];*/
/*
$emailarr = explode('#!!#',$inpEmailList);


$arr = array();

$out_arr = array("resposnse"=>null,
"code" => null,
"data"=>null,
);



foreach($emailarr as $email){
    $query = "SELECT `log_stat` FROM car_location WHERE `email` = '".$email."'";
    $sql =  mysqli_query($conn, $query);
    $row = mysqli_fetch_array($sql);
    
    array_push($arr,$row['log_stat']);
    
}

echo json_encode($arr);
    */
    
    
    

?>
