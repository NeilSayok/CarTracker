<?php
// $servername = "<hostname>";
// $user = "<user name>";
// $password = "<password>";
// $database = "<database name>";
// $conn = mysqli_connect($servername,$user,$password,$database);

require_once 'connection.php';


$inpemail = $_POST['email'];
$inppsw = $_POST['psw'];

$query = "SELECT * FROM `car_location` WHERE `email` = '".$inpemail."' OR `reg_id` = '".$inpemail."'";

if($result = mysqli_query($conn,$query)){

    foreach($result as $row) {
        print_r($row);
    }

    $row = mysqli_fetch_array($result);

    echo json_encode($row);
    
    // if(count($row) > 0)
    //     if($row['password'] == $inppsw){
    //         $array = array('name' => $row['name'],'reg_id' => $row['reg_id'],'email' => $row['email'], 'verified' => $row['verified'],'status' => 'OK');
    //     }else{
    //         $array = array('status' => 'passMiss','email'=> $inpemail);
    //     }
    // else{
    //         $array = array('status' => 'credMiss','email'=> $inpemail);
    //     } 
        
    //     echo json_encode($array);

}

?>
