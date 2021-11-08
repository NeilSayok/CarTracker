package com.example.neil.carlocator4l.API.Data


import com.google.gson.annotations.SerializedName

data class StatusData(
    @SerializedName("response")
    val response: String? = null,
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val `data`: List<Data>? = null
)