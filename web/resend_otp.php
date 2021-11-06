<?php

require_once 'connection.php';
require_once 'credentials.php';

require '../vendor/autoload.php';


use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;
use PHPMailer\PHPMailer\SMTP;

$out_arr = array("response"=>"",
"code" => "",
);

$variable = array();
$variable['email'] = $_POST['email'];
$variable['name'] = "";
$variable['otp'] = "";
$variable['reg_id'] = "";

$variable['name'] = getName($variable['email']);
$variable['reg_id'] = getVehId($variable['email']);
$variable['otp'] = mt_rand(100000, 999999);
while (isOTPPresent($variable['otp'])) {
    $variable['otp'] = mt_rand(100000, 999999);
}

$email = $variable['email'] ;
$name =  $variable['name'];
$otp = $variable['otp'];
$vehid = $variable['reg_id'];

$query = "UPDATE `OTP` SET `otp` = '".$otp."' WHERE `OTP`.`email` = '".$email."'";

mysqli_query($conn, $query);
//echo "Success<br>";

//echo $email."<br>".$name."<br>".$otp;


$template = file_get_contents("email.html");

$template = str_replace('##% name %##', $variable['name'], $template);
$template = str_replace('##% otp %##', $variable['otp'], $template);
$template = str_replace('##% email %##', $variable['email'], $template);
$template = str_replace('##% regid %##', $variable['reg_id'], $template);

$mail = new PHPMailer;
$mail->isSMTP();
$mail->SMTPDebug = 0; // 0 = off (for production use) - 1 = client messages - 2 = client and server messages
$mail->Host = $smtp_server;// use $mail->Host = gethostbyname('smtp.gmail.com'); // if your network does not support SMTP over IPv6
$mail->Username = $login;
$mail->Password = $password;
$mail->Port = 587; // TLS only
$mail->SMTPSecure = 'tls'; // ssl is deprecated
$mail->SMTPAuth = true;
$mail->setFrom($sent_from_emial, $sent_from_name); // From email and name
$mail->addAddress($variable['email'], $variable['name']); // to email and name
$mail->Subject = 'Registration OTP (Do not share!)';
$mail->msgHTML($template); //$mail->msgHTML(file_get_contents('contents.html'), __DIR__); //Read an HTML message body from an external file, convert referenced images to embedded,
$mail->AltBody = 'Your OTP is '.$variable['otp']; // If html emails is not supported by the receiver, show this body
// $mail->addAttachment('images/phpmailer_mini.png'); //Attach an image file

if (!$mail->send()) {
    $out_arr["response"] = "Mail_not_sent";
    $out_arr["code"] = 301;
} else {
    $out_arr["response"] = "Mail_sent";
    $out_arr["code"] = 300;
}

echo json_encode($out_arr);



function getOTP($email)
{
    $query = "SELECT otp from OTP where email = '".$email."'";
    if ($sql = mysqli_query($GLOBALS['conn'], $query)) {
        $row = mysqli_fetch_array($sql);
        //echo mysqli_num_rows($sql)."<br>";
        //echo $row['otp']."<br>";
        return $row['otp'];
    }
    //echo "Querry not success from getOTP<br>";
    return "<br>";
}


function getName($email)
{
    $query = "SELECT name from car_location where email = '".$email."'";
    if ($sql = mysqli_query($GLOBALS['conn'], $query)) {
        $row = mysqli_fetch_array($sql);

        return $row['name'];
    }

    return "";
}

function getVehId($email)
{
    $query = "SELECT reg_id from car_location where email = '".$email."'";
    if ($sql = mysqli_query($GLOBALS['conn'], $query)) {
        $row = mysqli_fetch_array($sql);

        return $row['reg_id'];
    }

    return "";
}

function isEmailPresent($email)
{
    $query = "SELECT email FROM OTP where email = '".$email."'";
    if ($sql =  mysqli_query($GLOBALS['conn'], $query)) {
        if (mysqli_num_rows($sql) >= 1) {
            //echo "Email Present<br> ";
            return true;
        } else {
            //echo "Email Not Present<br> ";
            return false;
        }
    }
    //echo "Email Not Presented<br> ";
    return false;
}

function isOTPPresent($otp)
{
    $query = "SELECT otp FROM OTP where otp = '".$otp."'";
    if ($sql =  mysqli_query($GLOBALS['conn'], $query)) {
        if (mysqli_num_rows($sql) >= 1) {
            //echo "OTP Present<br> ";
            return true;
        } else {
            // echo "OTP Not Present<br> ";
            return false;
        }
    }
    //echo "OTP Not Presented<br> ";
    return false;
}

function test($variable)
{
    echo "isEmailPresent()___________________________<br>";
    isEmailPresent($variable['email']);
    echo "<br>isOTPPresent()_________________________<br>";
    isOTPPresent($variable['otp']);
    echo "<br>getOTP()_______________________________<br>";
    getOTP($variable['email']);
    echo "<br>getName()______________________________<br>";
    getName($variable['email']);
    echo "_______________________________________<br>";
}

mysqli_close($conn);
