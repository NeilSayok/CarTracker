<?php
function encrypt_decrypt($string, $action = 'encrypt')
{
    $encrypt_method = "AES-256-CBC";
    $secret_key = 'Mypassword1#'; // user define private key
    $secret_iv = ''; // user define secret key
    $key = hash('sha256', $secret_key);
    $iv = substr(hash('sha256', $secret_iv), 0, 16); // sha256 is hash_hmac_algo
    if ($action == 'encrypt') {
        $output = openssl_encrypt($string, $encrypt_method, $key, 0, $iv);
        $output = base64_encode($output);
    } else if ($action == 'decrypt') {
        $output = openssl_decrypt(base64_decode($string), $encrypt_method, $key, 0, $iv);
    }
    return $output;
}


echo "Your Encrypted password is = ". $pwd = encrypt_decrypt('Mypassword1#', 'encrypt');
echo " Your Decrypted password is = ". encrypt_decrypt($pwd, 'decrypt');