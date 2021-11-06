<?php

require '../vendor/autoload.php';
require_once 'connection.php';
require_once 'credentials.php';
require_once 'response.php';


$inpemail = $_POST['email'];
$inplogstat = $_POST['stat'];

$sql = "UPDATE car_location SET `log_stat` = '".$inplogstat."' WHERE `email` = '".$inpemail."'";

echo mysqli_query($conn,$sql); 
    
    


?>
