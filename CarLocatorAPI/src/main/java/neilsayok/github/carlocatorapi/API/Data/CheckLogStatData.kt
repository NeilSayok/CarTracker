package neilsayok.github.carlocatorapi.API.Data


import com.google.gson.annotations.SerializedName

data class CheckLogStatData(
    @SerializedName("response")
    val response: String? = null,
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("stat")
    val stat: Int? = null
)