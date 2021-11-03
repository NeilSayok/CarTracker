<?php

$server_hash = hash_hmac('sha256', "asd", time());
echo $server_hash;
