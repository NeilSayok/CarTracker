package com.example.neil.carlocator4l.API.Data


import com.google.gson.annotations.SerializedName

data class CheckInDbData(
    @SerializedName("hash")
    val hash: String = "",
    @SerializedName("name")
    val name: String?,
    @SerializedName("present")
    val present: Boolean = false,
    @SerializedName("reg_id")
    val regId: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("verified")
    val verified: String?
)