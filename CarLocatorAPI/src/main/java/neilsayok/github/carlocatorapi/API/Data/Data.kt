package neilsayok.github.carlocatorapi.API.Data


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("reg_id")
    val regId: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("verified")
    val verified: Int = 0,
    @SerializedName("latitude")
    val latitude: String? = null,
    @SerializedName("longitude")
    val longitude: String? = null,
    @SerializedName("time")
    val time: Long? = null,
    @SerializedName("log_stat")
    val logStat: Int = 0
)