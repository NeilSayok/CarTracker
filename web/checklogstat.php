<?php

require '../vendor/autoload.php';
require_once 'connection.php';
require_once 'credentials.php';
require_once 'response.php';



$inpemail = $_POST['email'];

$sql = "SELECT `log_stat` FROM car_location WHERE `email` = '".$inpemail."'";

$result = mysqli_query($conn,$sql); 


while($row=mysqli_fetch_array($result))
{
    echo $row['log_stat'];
}



?>
