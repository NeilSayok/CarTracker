<?php

require '../vendor/autoload.php';
require_once 'connection.php';
require_once 'credentials.php';
require_once 'response.php';


    
$inpemail = $_POST['email'];
$inplat = $_POST['lat'];
$inplong = $_POST['longi'];
$inptime = $_POST['time'];

$out_arr = array("response"=>null,
"code" => null,
);


if (!isEmailPresent($inpemail)){
    $out_arr["response"] = $update_location_email_not_present[0];
    $out_arr["code"] = $update_location_email_not_present[1];

}else{
    $sql = "UPDATE car_location SET `latitude` = '".$inplat."', `longitude`= '".$inplong."', `time`= '".$inptime."' WHERE `email` = '".$inpemail."'";
    
    if(mysqli_query($conn,$sql)){
        $out_arr["response"] = $update_location_success[0];
        $out_arr["code"] = $update_location_success[1];
    }else{
        $out_arr["response"] = $update_location_error[0];
        $out_arr["code"] = $update_location_error[1];
    }
}


echo json_encode($out_arr);


function isEmailPresent($email){
    $query = "SELECT email FROM car_location where email = '".$email."'";
    if ($sql =  mysqli_query($GLOBALS['conn'], $query))
    {
        if(mysqli_num_rows($sql) >= 1){
            
            return true;
        }else{
           
            return false;
        }
    }
   
    return false;
}
?>
