package com.example.neil.carlocator4l.API.Data


import com.google.gson.annotations.SerializedName

data class WriteLogStatData(
    @SerializedName("response")
    val response: String? = null,
    @SerializedName("code")
    val code: Int? = null
)