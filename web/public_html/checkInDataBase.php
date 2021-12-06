<?php

require '../vendor/autoload.php';
require_once 'connection.php';
require_once 'credentials.php';
require_once 'response.php';

$time = time();
$out_arr = array("present" => $user_present_in_db_false, 
"name" => null, 
"reg_id" => null,
"verified"  => null,
"email" => null,
"hash" => encrypt_decrypt($time,$GLOBALS['server_hash']));


$inpemail = $_POST['email'];
$inpvehid = $_POST['vehid'];

// //Check if inpemail and inpvehid are not empty
// echo "inpemail: ".$inpemail."<br>";
// echo "inpvehid: ".$inpvehid."<br>";
// creatTempHash();

    $querry = "SELECT count(*) as totalitems ,`name`,`email`,`reg_id`,`verified` FROM car_location WHERE `email` = '".$inpemail."' OR `reg_id` = '".$inpvehid."'";
    if($result = mysqli_query($conn,$querry)){
        $row = mysqli_fetch_array($result);
        if($row['totalitems'] > 0){
            $out_arr["present"] = $user_present_in_db_true;
            $out_arr["name"] = $row['name'];
            $out_arr["reg_id"] = $row['reg_id'];
            $out_arr["email"] = $row['email'];
            $out_arr["verified"] = $row['verified'];
            $out_arr["hash"] = $row['password'];
        }else{
            $query = "INSERT INTO temp_hash(hashkey,timestamp) VALUES('".$out_arr["hash"]."','$time')";
            //mysqli_query($GLOBALS['conn'],$query);
        }
       
    }
    
        
echo json_encode($out_arr);

?>