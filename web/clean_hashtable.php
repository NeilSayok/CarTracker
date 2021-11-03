<?php

require_once 'connection.php';
require_once 'credentials.php';
require_once 'response.php';

$query = "DELETE FROM temp_hash WHERE timestamp <= UNIX_TIMESTAMP() - 3600";
mysqli_query($conn,$query);