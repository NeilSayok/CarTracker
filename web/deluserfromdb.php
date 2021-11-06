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



$inpemail = $_POST['email'];

$sql = "DELETE FROM `car_location` WHERE `car_location`.`email` = '".$inpemail."'";

$result = mysqli_query($conn,$sql);

$sql =  "DELETE FROM `OTP` WHERE `OTP`.`email` = '".$inpemail."'";

$result = mysqli_query($conn,$sql);


    
    
    
?>
