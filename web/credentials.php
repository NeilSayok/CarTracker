<?php

//Mypassword1# hashed with MD5
$server_hash = $server_hash = hash_hmac('sha1', $_SERVER['HTTP_HOST'], '3c990de67d9ab60b451c743d4afbd032');;

$smtp_server= "smtp-relay.sendinblue.com";
$login= "blue.labs.dev@gmail.com";
$password= "ULHYfM0nOvVm5EDF";
$sent_from_emial = "donotreply@car-locator.herokuapp.com";
$sent_from_name = "Project Travel System";

