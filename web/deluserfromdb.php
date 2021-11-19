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


$out_arr = array("response"=>null,
"code" => null,
);



$inpemail = $_POST['email'];

if (!isEmailPresent($inpemail)){

    $out_arr["response"] = $delete_user_from_db_email_not_present[0];
    $out_arr["code"] = $delete_user_from_db_email_not_present[1];

}else{

    $sql = "DELETE FROM `car_location` WHERE `car_location`.`email` = '".$inpemail."'";

    if( mysqli_query($conn,$sql)){
        $sql =  "DELETE FROM `OTP` WHERE `OTP`.`email` = '".$inpemail."'";
        if( mysqli_query($conn,$sql)){
            $out_arr["response"] = $delete_user_from_db_success[0];
            $out_arr["code"] = $delete_user_from_db_success[1];
        }else{
            $out_arr["response"] = $delete_user_from_db_otp_not_removed[0];
            $out_arr["code"] = $delete_user_from_db_otp_not_removed[1];
        }
    }else{
        $out_arr["response"] = $delete_user_from_db_error[0];
        $out_arr["code"] = $delete_user_from_db_error[1];
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
