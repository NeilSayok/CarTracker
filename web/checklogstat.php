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
    $out_arr["response"] = "null_value_passed";
    $out_arr["code"] = 1001;
    
}else if (!isEmailPresent($inpemail)){
    $out_arr["response"] = "email_not_present";
    $out_arr["code"] = 1002;
    
}else{
    $sql = "SELECT `log_stat` FROM car_location WHERE `email` = '".$inpemail."'";
    if ($result = mysqli_query($conn, $sql)) {
        $row = mysqli_fetch_assoc($result);
        $out_arr["stat"] = $row["log_stat"];
        $out_arr["code"] = 1000;
    }else{
        $out_arr["response"] = "error_in_query";
        $out_arr["code"] = 1003;
    }
    
}




while($row=mysqli_fetch_array($result))
{
    echo $row['log_stat'];
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
