<?php
 
 
// $servername = "<hostname>";
// $user = "<user name>";
// $password = "<password>";
// $database = "<database name>";
// $conn = mysqli_connect($servername,$user,$password,$database);

require '../vendor/autoload.php';


require_once 'connection.php';
use Egulias\EmailValidator\EmailValidator;
use Egulias\EmailValidator\Validation\RFCValidation;
 
 // Check connection
if ($conn->connect_error) 
{
    die("Connection failed: " . $conn->connect_error);
}
else{
	
	$inpname = $_POST['name'];
	$inpvehId = $_POST['vehid'];
	$inpemail = $_POST['email'];
	$inppsw = $_POST['psw'];
	$inprpsw = $_POST['rpsw'];

    echo json_encode(array('POST[inpname]'=>$inpname));
    echo json_encode(array('POST[inpvehId]'=>$inpvehId));
    echo json_encode(array('POST[email]'=>$inpemail));
    echo json_encode(array('POST[inppsw]'=>$inppsw));
    echo json_encode(array('POST[inprpsw]'=>$inprpsw));
	
    if(is_null($inpname) || is_null($inpvehId)|| is_null($inpemail) || is_null($inppsw) || is_null($inprpsw)){
        echo json_encode(array('response'=>'Null_Value_Restricted'));
    }else if($inppsw != $inprpsw)
    {
	    echo json_encode(array('response'=>'Password_Missmatch'));
    }
    else if(!isValidEmail($inpemail)){
        
        echo json_encode(array('response'=>'Email_format_wrong'));
        
    }else if (isEmailPresent($inpemail)) {
      	  echo json_encode(array('response'=>'Email_Already_Pressent'));	
    }
	else
	{
		
	    $sql = "INSERT INTO `car_location` (`name`, `email`, `reg_id` , `password` )VALUES ('$inpname' , '$inpemail' , '$inpvehId' , '$inppsw')";

		if (mysqli_query($conn, $sql))
		{
			echo json_encode(array('response'=>'Account_Created'));
			 
		} 
		else
		{
		    echo json_encode(array('response'=>'Account_Not_Success'));
		}
	}
}

function isEmailPresent($email){
    echo json_encode(array('email'=>$email));
    $query = "SELECT email FROM car_location where email = '".$email."'";
    echo json_encode(array('query'=>$query));
    if ($sql =  mysqli_query($GLOBALS['conn'], $query))
    {
        while($row = mysqli_fetch_array($sql))
        {
            print_r($row);
        }
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
