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
               @Field("rpsw") rpsw:String? = null,): Call<SimpleResponseData>

    @FormUrlEncoded
    @POST("loginA.php")
    fun login(@Field("email") email:String? = null,
              @Field("psw") password:String? = null,): Call<LoginData>

    @FormUrlEncoded
    @POST("send_otp.php")
    fun send_otp(@Field("email") email:String? = null): Call<SimpleResponseData>

    @FormUrlEncoded
    @POST("resend_otp.php")
    fun resend_otp(@Field("email") email:String? = null): Call<SimpleResponseData>

    @FormUrlEncoded
    @POST("verify_otp.php")
    fun verify_otp(@Field("otp_conf") otp:String? = null,
                   @Field("email") email:String? = null): Call<SimpleResponseData>

    @FormUrlEncoded
    @POST("writelogstat.php")
    fun write_log_stat(@Field("email") email:String? = null,
                   @Field("stat") stat:Int = 0): Call<SimpleResponseData>

    @FormUrlEncoded
    @POST("updatelocation.php")
    fun update_location(@Field("email") email:String? = null,
                        @Field("lat") latitude: Double? = null,
                        @Field("longi") longitude: Double? = null,
                        @Field("time") time: Long? = null): Call<SimpleResponseData>

    @FormUrlEncoded
    @POST("deluserfromdb.php")
    fun del_user_from_db(@Field("email") email:String? = null,): Call<SimpleResponseData>

    @FormUrlEncoded
    @POST("checklogstat.php")
    fun check_log_stat(@Field("email") email:String? = null,): Call<CheckLogStatData>


}