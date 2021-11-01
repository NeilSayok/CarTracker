<?php

require_once 'connection.php';
require_once 'credentials.php';

require '../vendor/autoload.php';



try{

    
$variable = array();
$variable['email'] = $_POST['email'];
$variable['name'] = "";
$variable['otp'] = "";
$variable['reg_id'] = "";

if (isEmailPresent($variable['email'])) {
    $variable['name'] = getName($variable['email']);
    $variable['otp'] = getOTP($variable['email']);
    $variable['reg_id'] = getVehId($variable['email']);
} else {
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

    $query =  "INSERT INTO OTP (email, name, otp, regid) VALUES ('$email' , '$name' , '$otp' , '$vehid')";
    $res = mysqli_query($conn, $query);
}

$template = file_get_contents("email.html");

$template = str_replace('##% name %##', $variable['name'], $template);
$template = str_replace('##% otp %##', $variable['otp'], $template);
$template = str_replace('##% email %##', $variable['email'], $template);
$template = str_replace('##% regid %##', $variable['reg_id'], $template);





$transport = (new Swift_SmtpTransport($hostname, 587, 'tls'))
  ->setUsername($username)
  ->setPassword($password);

$mailer = new Swift_Mailer($transport);
$message = (new Swift_Message())
  ->setSubject('Registration OTP (Do not share!)')
  ->setFrom(['noreply@car-locator.herokuapp.com'])
  ->setTo([$variable['email'] => $variable['name']]);

$headers = ($message->getHeaders())
  -> addTextHeader('X-CloudMTA-Class', 'standard');

$message->setBody($template);
$message->addPart('Your OTP is '.$variable['otp'], 'text/plain');
$out = $mailer->send($message);
echo "Mail Sent " .$out;

}catch(Exception $e){
    echo 'Message: ' .$e->getMessage();
}




function getOTP($email)
{
    $query = "SELECT otp from OTP where email = '".$email."'";
    if ($sql = mysqli_query($GLOBALS['conn'], $query)) {
        $row = mysqli_fetch_array($sql);

        return $row['otp'];
    }

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
    echo $query;
    if ($sql =  mysqli_query($GLOBALS['conn'], $query)) {
        if (mysqli_num_rows($sql) >= 1) {
            return true;
        } else {
            return false;
        }
    }

    return false;
}

function isOTPPresent($otp)
{
    $query = "SELECT otp FROM OTP where otp = '".$otp."'";
    if ($sql =  mysqli_query($GLOBALS['conn'], $query)) {
        if (mysqli_num_rows($sql) >= 1) {
            return true;
        } else {
            return false;
        }
    }

    return false;
}
