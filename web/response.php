<?php

$user_present_in_db_true = true;
$user_present_in_db_false = false;


//-----------------------SignupA-----------------------------//
//--------------------Code Grp:100--------------------------//

$account_created = ["Account_Created",100];
$null_value_restricted = ["Null_Value_Restricted",101];
$password_missmatch = ["Password_Missmatch",102];
$email_format_wrong = ["Email_format_wrong",103];
$email_already_pressent = ["Email_Already_Pressent",104];
$account_not_success = ["Account_Not_Success",105];

//---------------------------------------------------------//

//----------------------Send_otp----------------------------//
//--------------------Code Grp:200--------------------------//

$mail_sent = ["Mail_sent", 200];
$mail_not_sent = ["Mail_not_sent",201];

//---------------------------------------------------------//

//----------------------resend_otp----------------------------//
//--------------------Code Grp:300--------------------------//

$re_mail_sent = ["Mail_sent", 300];
$re_mail_not_sent = ["Mail_not_sent",301];

//---------------------------------------------------------//

//----------------------loginA----------------------------//
//--------------------Code Grp:400--------------------------//

$success = ["success", 400];
$passMiss = ["passMiss",401];
$credMiss = ["credMiss",402];
$null_value_not_allowed = ["Null_Value_Not_Allowed",403];
//---------------------------------------------------------//

//----------------------statusGetterA----------------------------//
//--------------------Code Grp:500--------------------------//

$stat_online = ["id_stat_online",500];
$stat_offline = ["id_stat_offline", 501];
$stat_mail_missing = ["email_empty", 502];

//---------------------------------------------------------//

//----------------------verify_otp----------------------------//
//--------------------Code Grp:600--------------------------//

$stat_otp_verified = ["verified", 600];
$stat_otp_not_verified = ["not_verified",601];
$stat_otp_error = ["server_error",602];
//---------------------------------------------------------//

//----------------------writelogstat----------------------------//
//--------------------Code Grp:700--------------------------//

$log_stat_write_success = ["success", 700];
$log_stat_write_error = ["error",701];
$log_stat_mail_not_present = ["mail_not_present",702];
//---------------------------------------------------------//

//----------------------updatelocation----------------------------//
//--------------------Code Grp:800--------------------------//

$update_location_success = ["updated", 800];
$update_location_error = ["Error",801];
$update_location_email_not_present = ["mail_not_present",802];
$update_location_empty_param = ["empty_param",803];
//---------------------------------------------------------//

//----------------------deleteuserfromdb----------------------------//
//--------------------Code Grp:900--------------------------//

$delete_user_from_db_success = ["success", 900];
$delete_user_from_db_error = ["error_user_not_removed",901];
$delete_user_from_db_email_not_present = ["mail_not_present",902];
$delete_user_from_db_otp_not_removed = ["error_otp_not_removed", 903];

//---------------------------------------------------------//

//----------------------checklogstat----------------------------//
//--------------------Code Grp:1000--------------------------//

$check_log_stat_success = ["success", 1000];
$check_log_stat_null_value_passed = ["null_value_passed",1001];
$check_log_stat_email_not_present = ["email_not_present",1002];
$check_log_stat_error_in_query = ["error_in_query", 1003];

//---------------------------------------------------------//