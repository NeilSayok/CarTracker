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

use Egulias\EmailValidator\EmailValidator;
use Egulias\EmailValidator\Validation\RFCValidation;
 

$inpname = $_POST['name'];
$inpvehId = $_POST['vehid'];
$inpemail = $_POST['email'];
$inppsw = $_POST['psw'];
$inprpsw = $_POST['rpsw'];
$inphash = $_POST['hash'];

$out_arr = array("resposnse"=>"",
"code" => "",
);


if(empty($inpname) || empty($inpvehId)|| empty($inpemail) || empty($inppsw) || empty($inprpsw) ){
    $out_arr["response"] = "Null_Value_Restricted";
    $out_arr["code"] = 101;
    //echo json_encode(array('response'=>'Null_Value_Restricted'));
}else if($inppsw != $inprpsw)
{
    $out_arr["response"] = "Password_Missmatch";
    $out_arr["code"] = 102;
    //echo json_encode(array('response'=>'Password_Missmatch'));
}
else if(!isValidEmail($inpemail)){
    $out_arr["response"] = "Email_format_wrong";
    $out_arr["code"] = 103;
    //echo json_encode(array('response'=>'Email_format_wrong'));
    
}else if (isEmailPresent($inpemail)) {
        $out_arr["response"] = "Email_Already_Pressent";
        $out_arr["code"] = 104;
        //echo json_encode(array('response'=>'Email_Already_Pressent'));	
}else{

    //$inppsw =  encrypt_decrypt($inppsw,$inphash,"decrypt");
        $sql = "INSERT INTO `car_location` (`name`, `email`, `reg_id` , `password` )VALUES ('$inpname' , '$inpemail' , '$inpvehId' , '$inppsw')";
        if (mysqli_query($conn, $sql)){
            $out_arr["response"] = "Account_Created";
            $out_arr["code"] = 200;
        }else{
            $out_arr["response"] = "Account_Not_Success";
            $out_arr["code"] = 105;
            //echo json_encode(array('response'=>'Account_Not_Success'));
        }
    // if(hashIsCorrect($inphash)){
        
    // }else{
    //     echo json_encode(array('response'=>'Hash_Mismatch'));	
    // }


    
}

echo json_encode($out_arr);

function hashIsCorrect($inphash){
    $sql = "SELECT EXISTS(SELECT * FROM temp_hash WHERE hashkey = '$inphash')";
    $result = mysqli_query($conn, $sql);
    $row = mysqli_fetch_array($result);
    if($row[0] == 1){
        return true;
    return false;
    }
}


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
//Not working properly
function isValidEmail($email) {
    $validator = new EmailValidator();
    return $validator->isValid($email, new RFCValidation());  
}

mysqli_close($conn);
?>
