<?php
//DB on https://www.freemysqlhosting.net/login/?checkemail=registered
define('servername', '<server_name>');
define('user', '<db_user_name>');
define('password', '<db_password>');
define('database', '<db_name>');

$conn = mysqli_connect(servername, user, password, database) or die("Connection failed: " . $conn->connect_error);

$query_create_table_car_location ="CREATE TABLE IF NOT EXISTS OTP(id int(10) NOT NULL AUTO_INCREMENT,email varchar(1000),name varchar(1000),otp int(6),regid varchar(20),PRIMARY KEY (id));";

$query_create_table_otp ="CREATE TABLE IF NOT EXISTS car_location(
    id int(10) NOT NULL AUTO_INCREMENT,
    name varchar(1000),email varchar(1000),
    reg_id varchar(20),password varchar(1000),
    verified tinyint(4),
    latitude varchar(50) NULL,
    longitude varchar(50) NULL,
    time varchar(50) NULL,
    log_stat tinyint(4),
    PRIMARY KEY (id));";

if ($conn->connect_error)
{
    die("Connection failed: " . $conn->connect_error);
}else{
    mysqli_query($conn,$query_create_table_car_location);
    mysqli_query($conn,$query_create_table_otp);
}