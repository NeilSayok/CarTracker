package neilsayok.github.carlocatorapi.API

data class ResponseCodes(

//-----------------------SignupA-----------------------------//
//--------------------Code Grp:100--------------------------//

    val responses: Map<String, Int> = mapOf("Account_Created" to 100,
        "Null_Value_Restricted" to 101,
        "Password_Missmatch" to 102,
        "Email_format_wrong" to 103,
        "Email_Already_Pressent" to 104,
        "Account_Not_Success" to 105,

        //---------------------------------------------------------//

        //----------------------Send_otp----------------------------//
        //--------------------Code Grp:200--------------------------//

        "OTP_Mail_sent" to  200,
        "OTP_Mail_not_sent" to 201,

        //---------------------------------------------------------//

        //----------------------resend_otp----------------------------//
        //--------------------Code Grp:300--------------------------//

        "OTP_Mail_resent" to  300,
        "OTP_Mail_not_resent" to 301,

        //---------------------------------------------------------//

        //----------------------loginA----------------------------//
        //--------------------Code Grp:400--------------------------//
        "success" to  400,
        "passMiss" to 401,
        "credMiss" to 402,
        "Null_Value_Not_Allowed" to 403,
        //---------------------------------------------------------//

        //----------------------statusGetterA----------------------------//
        //--------------------Code Grp:500--------------------------//
        "id_stat_offline" to  501,
        "id_stat_online" to 500,
        //---------------------------------------------------------//

        //----------------------verify_otp----------------------------//
        //--------------------Code Grp:600--------------------------//

        "stat_otp_verified" to 600,
        "stat_otp_not_verified" to 601,
        "stat_otp_error" to 602,
        //---------------------------------------------------------//

        //----------------------writelogstat----------------------------//
        //--------------------Code Grp:700--------------------------//

        "log_stat_write_success" to 700,
        "log_stat_write_error" to 701,
        "log_stat_mail_not_present" to 702,
        //---------------------------------------------------------//


        //----------------------updatelocation----------------------------//
        //--------------------Code Grp:800--------------------------//
        
        "update_location_success" to  800,
        "update_location_error" to 801,
        "update_location_email_not_present" to 802,
        //---------------------------------------------------------//
        
        //----------------------deleteuserfromdb----------------------------//
        //--------------------Code Grp:900--------------------------//
        
        "delete_user_from_db_success" to  900,
        "delete_user_from_db_error" to 902,
        "delete_user_from_db_email_not_present" to 903,
        "delete_user_from_db_otp_not_removed" to  904,
        
        //---------------------------------------------------------//
)
)
