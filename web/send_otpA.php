<?php
require('vendor/autoload.php');

require_once 'credentials.php';

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
$mailer->send($message);