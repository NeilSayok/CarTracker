<?php

//Mypassword1# hashed with MD5
$server_hash = generatePasswordHash($_SERVER['HTTP_HOST'],'3c990de67d9ab60b451c743d4afbd032');


$smtp_server= "smtp-relay.sendinblue.com";
$login= "blue.labs.dev@gmail.com";
$password= "ULHYfM0nOvVm5EDF";
$sent_from_emial = "donotreply@car-locator.herokuapp.com";
$sent_from_name = "Project Travel System";


function generatePasswordHash($password,$salt) {
    return hash_hmac('sha256', $password, $salt);
}


function encrypt_decrypt($string, $action = 'encrypt')
{
    $encrypt_method = "AES-256-CBC";
    $secret_key = 'AA74CDCC2BBRT935136HH7B63C27'; // user define private key
    $secret_iv = '5fgf5HJ5g27'; // user define secret key
    $key = hash('sha256', $secret_key);
    $iv = substr(hash('sha256', $secret_iv), 0, 16); // sha256 is hash_hmac_algo
    if ($action == 'encrypt') {
        $output = openssl_encrypt($string, $encrypt_method, $key, 0, $iv);
        $output = base64_encode($output);
    } else if ($action == 'decrypt') {
        $output = openssl_decrypt(base64_decode($string), $encrypt_method, $key, 0, $iv);
    }
    return $output;
}
