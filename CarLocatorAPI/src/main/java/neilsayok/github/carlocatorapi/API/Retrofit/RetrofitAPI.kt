package neilsayok.github.carlocatorapi.API.Retrofit

import neilsayok.github.carlocatorapi.API.Data.*
import retrofit2.Call
import retrofit2.http.*


public interface RetrofitAPI {

    @FormUrlEncoded
    @POST("checkInDataBase.php")
    fun checkUserinDB(@Field("email") email:String? = null,
                      @Field("vehid") vehid:String?=null): Call<CheckInDbData>

    @FormUrlEncoded
    @POST("statusGetterA.php")
    fun statusGetter(@Field("emails") emails:String? = null): Call<StatusData>


    @FormUrlEncoded
    @POST("signUpA.php")
    fun signup(@Field("name") name:String? = null,
               @Field("vehid") vehid:String? = null,
               @Field("email") email:String? = null,
               @Field("psw") psw:String? = null,
               @Field("rpsw") rpsw:String? = null,): Call<CreateAccountData>

    @FormUrlEncoded
    @POST("loginA.php")
    fun login(@Field("email") email:String? = null,
              @Field("psw") password:String? = null,): Call<LoginData>

    @FormUrlEncoded
    @POST("send_otp.php")
    fun send_otp(@Field("email") email:String? = null): Call<OTPData>

    @FormUrlEncoded
    @POST("resend_otp.php")
    fun resend_otp(@Field("email") email:String? = null): Call<OTPData>

    @FormUrlEncoded
    @POST("verify_otp.php")
    fun verify_otp(@Field("otp_conf") otp:String? = null,
                   @Field("email") email:String? = null): Call<VerifyOTPData>

    @FormUrlEncoded
    @POST("writelogstat.php")
    fun write_log_stat(@Field("email") email:String? = null,
                   @Field("stat") stat:Int = 0): Call<WriteLogStatData>




}