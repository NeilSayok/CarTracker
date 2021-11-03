<?php

require_once 'connection.php';
require_once 'credentials.php';
require_once 'response.php';

$query = "DELETE FROM temp_hash WHERE timestamp <= UNIX_TIMESTAMP() - 86400";
mysqli_query($conn,$query);