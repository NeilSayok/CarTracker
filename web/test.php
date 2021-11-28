<?php

$str = explode(',', "neil@1234, neil@1234");

foreach($str as $email){
  echo trim($email)."\n";
}


