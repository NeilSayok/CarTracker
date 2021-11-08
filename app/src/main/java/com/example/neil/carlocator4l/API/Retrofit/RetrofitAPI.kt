package com.example.neil.carlocator4l.API.Retrofit

import com.example.neil.carlocator4l.API.Data.CheckInDbData
import com.example.neil.carlocator4l.API.Data.CreateAccountData
import com.example.neil.carlocator4l.API.Data.LoginData
import com.example.neil.carlocator4l.API.Data.StatusData
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
    fun login(@Field("emails") email:String? = null,
              @Field("psw") password:String? = null,): Call<LoginData>


}