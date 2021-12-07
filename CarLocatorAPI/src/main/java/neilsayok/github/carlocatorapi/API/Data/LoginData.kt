package neilsayok.github.carlocatorapi.API.Data


import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("response")
    val response: String? = null,
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("reg_id")
    val regId: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("verified")
    val verified: String? = null
)