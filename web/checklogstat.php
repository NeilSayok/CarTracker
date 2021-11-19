<?php

require '../vendor/autoload.php';
require_once 'connection.php';
require_once 'credentials.php';
require_once 'response.php';

$out_arr = array("response"=>null,
"code" => null,
);



$inpemail = $_POST['email'];

if (!isEmailPresent($inpemail)){
    echo "email_not_present";
}else{
    
}

$sql = "SELECT `log_stat` FROM car_location WHERE `email` = '".$inpemail."'";

$result = mysqli_query($conn,$sql); 


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
