<?php

require_once 'connection.php';

$array = array("present" => null, 
"name" => null, 
"reg_id" => null,
"verified"  => null,
"hash" => null);


$inpemail = $_POST['email'];
$inpvehid = $_POST['vehid'];

//Check if inpemail and inpvehid are not empty
echo "inpemail: ".$inpemail."<br>";
echo "inpvehid: ".$inpvehid."<br>";

$querry = "SELECT `name`,`email`,`reg_id`,`password`,`verified` FROM car_location WHERE `email` = '".$inpemail."' OR `reg_id` = '".$inpvehid."'";

if($result = mysqli_query($conn,$querry)){
    $row = mysqli_fetch_array($result);
    if(count($row) > 0){
        $array = array("present" => "yes", "name" => $row['name'], "reg_id" => $row['reg_id'], "password" => $row['password'], "verified"  => $row['verified']);
    }
    else
            $array = array("present" => "no");
}else
    $array = array("present" => "err");

echo json_encode($array);
    


?>
