<?php

define('servername', 'ckshdphy86qnz0bj.cbetxkdyhwsb.us-east-1.rds.amazonaws.com');
define('user', 's1fik9aru9o2b7an');
define('password', 'ldtbatqicw6ex2y9');
define('database', 'n7zptp6z8wn8v3qs');

$conn = mysqli_connect(servername, user, password, database) or die("Connection failed: " . $conn->connect_error);
