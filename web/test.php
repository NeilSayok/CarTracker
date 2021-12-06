<?php

$array = array("name"=> "John", "age"=>"35", "pass"=>"123", "city"=>"New York", "country"=>"USA");

unset($array['age']);

echo json_encode($array);



