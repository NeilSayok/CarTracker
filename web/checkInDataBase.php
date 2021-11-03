<?php

require_once 'connection.php';
require_once 'credentials.php';

$out_arr = array("present" => null, 
"name" => null, 
"reg_id" => null,
"verified"  => null,
"hash" => null);


$inpemail = $_POST['email'];
$inpvehid = $_POST['vehid'];

// //Check if inpemail and inpvehid are not empty
// echo "inpemail: ".$inpemail."<br>";
// echo "inpvehid: ".$inpvehid."<br>";
creatTempHash();

    $querry = "SELECT `name`,`email`,`reg_id`,`password`,`verified` FROM car_location WHERE `email` = '".$inpemail."' OR `reg_id` = '".$inpvehid."'";
    if($result = mysqli_query($conn,$querry)){
        $row = mysqli_fetch_array($result);
        if(count($row) > 0){
            $out_arr["present"] = "yes";
            $out_arr["name"] = $row['name'];
            $out_arr["reg_id"] = $row['reg_id'];
            $out_arr["verified"] = $row['verified'];
            $out_arr["hash"] = $row['password'];
        }
       
    }
    
        


echo json_encode($out_arr);

function creatTempHash(){
    $time = time();
    $GLOBALS['out_arr']["present"] = "no";
    $GLOBALS['out_arr']["hash"] = hash_hmac('sha256',$time,$GLOBALS['server_hash']);   
    $query = "INSERT INTO temp_hash(hashkey,timestamp) VALUES('".$out_arr["hash"]."','$time')";
    mysqli_query($GLOBALS['conn'],$query);
}





?>
