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
'name' => null,
'reg_id' => null,
'email' => null,
'verified' => null,
'data' => null,
);

$inpemail = $_POST['email'];
$inppsw = $_POST['psw'];

if(empty($inpemail) || empty($inppsw)){
    $out_arr['code'] = $null_value_not_allowed[1];
    $out_arr['response'] = $null_value_not_allowed[0];
    echo json_encode($out_arr);
    exit();
}

$query = "SELECT * FROM `car_location` WHERE `email` = '".$inpemail."' OR `reg_id` = '".$inpemail."'";

if($result = mysqli_query($conn,$query)){

    $row = mysqli_fetch_array($result);
    
    if(is_array($row) && count($row) > 0){
        if($row['password'] == $inppsw){
            $out_arr['response'] = $success[0];
            $out_arr['code'] = $success[1];
            $out_arr['name'] = $row['name'];
            $out_arr['reg_id'] = $row['reg_id'];
            $out_arr['email'] = $row['email'];
            $out_arr['verified'] = $row['verified'];
            unset($array['password']);
            $out_rows = array();
            foreach ($row as $r){
                $out_rows[] = $r;
            }
            $out_arr['data'] = $out_rows;

            //$array = array('name' => $row['name'],'reg_id' => $row['reg_id'],'email' => $row['email'], 'verified' => $row['verified'],'status' => 'OK');
        }else{
            $out_arr['response'] =$passMiss[0];
            $out_arr['code'] = $passMiss[1];
            //$array = array('status' => 'passMiss','email'=> $inpemail);
        }
    }else{
            $out_arr['response'] = $credMiss[0];
            $out_arr['code'] = $credMiss[1];
            //$array = array('status' => 'credMiss','email'=> $inpemail);
        } 
        
        //echo json_encode($array);

}
echo json_encode($out_arr);
?>
