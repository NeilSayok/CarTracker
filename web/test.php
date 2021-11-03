<?php

$server_hash = hash_hmac('sha1', $_SERVER['HTTP_HOST'], '3c990de67d9ab60b451c743d4afbd032');
echo $server_hash;
