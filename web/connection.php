<?php
//DB on https://www.freemysqlhosting.net/login/?checkemail=registered

//mysql://b456e143358879:bb2dd12d@us-cdbr-east-04.cleardb.com/heroku_272798b743ae862?reconnect=true

define('servername', 'us-cdbr-east-04.cleardb.com');
define('user', 'b456e143358879');
define('password', 'bb2dd12d');
define('database', 'heroku_272798b743ae862');

$conn = mysqli_connect(servername, user, password, database) or die("Connection failed: " . $conn->connect_error);

$query_create_table_car_location ="CREATE TABLE IF NOT EXISTS OTP(
    id int NOT NULL AUTO_INCREMENT,
    email varchar(350),
    name varchar(350),
    otp varchar(6),
    regid varchar(20),
    PRIMARY KEY (id));";

$query_create_table_otp ="CREATE TABLE IF NOT EXISTS car_location(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(350),
    email varchar(350),
    reg_id varchar(20),
    password varchar(150),
    verified bit default 0,
    latitude varchar(50) NULL,
    longitude varchar(50) NULL,
    time int NULL,
    log_stat bit default 0,
    PRIMARY KEY (id));";

$query_create_table_temp_hash ="CREATE TABLE IF NOT EXISTS temp_hash(
    id int NOT NULL AUTO_INCREMENT,
    timestamp INT,
    hashkey varchar(32),
    PRIMARY KEY (id));";

if ($conn->connect_error)
{
    die("Connection failed: " . $conn->connect_error);
}else{
    mysqli_query($conn,$query_create_table_car_location);
    mysqli_query($conn,$query_create_table_otp);
    mysqli_query($conn,$query_create_table_temp_hash);
}