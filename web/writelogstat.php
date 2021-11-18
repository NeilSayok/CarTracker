<?php

require '../vendor/autoload.php';
require_once 'connection.php';
require_once 'credentials.php';
require_once 'response.php';

$out_arr = array("response"=>null,
"code" => null);

$inpemail = $_POST['email'];
$inplogstat = $_POST['stat'];

$sql = "UPDATE car_location SET `log_stat` = '".$inplogstat."' WHERE `email` = '".$inpemail."'";


if (isEmailPresent($inpemail)){
    $sql = "UPDATE car_location SET `log_stat` = '".$inplogstat."' WHERE `email` = '".$inpemail."'";
    if(mysqli_query($conn,$sql)){
        $out_arr["response"] = $log_stat_write_success[0];
        $out_arr["code"] = $log_stat_write_success[1];
    }else{
        $out_arr["response"] = $log_stat_write_error[0];
        $out_arr["code"] = $log_stat_write_error[1];
    }

}else{
    $out_arr["response"] = $log_stat_mail_not_present[0];
    $out_arr["code"] = $log_stat_mail_not_present[1];
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
