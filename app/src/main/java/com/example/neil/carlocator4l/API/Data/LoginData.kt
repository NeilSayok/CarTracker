package com.example.neil.carlocator4l.API.Data


import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("response")
    val response: String? = null,
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("name")
    val name: Any? = null,
    @SerializedName("reg_id")
    val regId: Any? = null,
    @SerializedName("email")
    val email: Any? = null,
    @SerializedName("verified")
    val verified: Any? = null
)