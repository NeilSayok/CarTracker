<?php

require '../vendor/autoload.php';
require_once 'connection.php';
require_once 'credentials.php';
require_once 'response.php';

$out_arr = array("response"=>null,
"code" => null,
"stat" => null,
);

$inpemail = $_POST['email'];

if(empty($inpemail)){
    $out_arr["response"] = $check_log_stat_null_value_passed[0];
    $out_arr["code"] = $check_log_stat_null_value_passed[1];
   
}else if (!isEmailPresent($inpemail)){
    $out_arr["response"] = $check_log_stat_email_not_present[0];
    $out_arr["code"] = $check_log_stat_email_not_present[1];
    
}else{
    $sql = "SELECT `log_stat` FROM car_location WHERE `email` = '".$inpemail."'";
    if ($result = mysqli_query($conn, $sql)) {
        $row = mysqli_fetch_assoc($result);
        $out_arr["response"] = $check_log_stat_success[0];
        $out_arr["stat"] = (int) $row["log_stat"];
        $out_arr["code"] = $check_log_stat_success[1];
    }else{
        $out_arr["response"] = $check_log_stat_error_in_query[0];
        $out_arr["code"] = $check_log_stat_error_in_query[1];
    }
    
}




// while($row=mysqli_fetch_array($result))
// {
//     echo $row['log_stat'];
// }

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
