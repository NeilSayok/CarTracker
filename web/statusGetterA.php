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

$inp = $_POST['emails'];
$inpEmailList = explode(',', $inp);

$out_arr = array("response"=>"stat_online",
"code" => 500,
"data"=>null,
);

echo count($inpEmailList);

if(empty($inp) || count($inpEmailList) == 0){
    $out_arr["response"] = $stat_mail_missing[0];
    $out_arr["code"] = $stat_mail_missing[1];
    $out_arr["data"] = null;
}else{

    $query = "SELECT `id`,`name`,`email`,`reg_id`,`verified`,`latitude`,`longitude`,`time`,`log_stat`
    FROM car_location WHERE `email` in (";

    foreach($inpEmailList as $email){
        $query .= "'".trim($email)."',";
    }
    $query = rtrim($query,',');
    $query .= ")";

    echo $query;

    $sql =  mysqli_query($conn, $query);

    if(empty($sql)){
        $out_arr["response"] = $stat_offline[0];
        $out_arr["code"] = $stat_offline[1];
        $out_arr["data"] = null;
    }
    else{
        $rows = array();
        while($r = mysqli_fetch_assoc($sql)) {
            $rows[] = $r;
        }
        $out_arr["response"] = $stat_online[0];
        $out_arr["code"] = $stat_online[1];
        $out_arr["data"] = $rows;
    }

}












echo json_encode($out_arr);

    
    
    
    

?>
