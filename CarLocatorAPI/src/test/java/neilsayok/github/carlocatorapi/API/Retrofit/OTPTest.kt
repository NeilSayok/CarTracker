package neilsayok.github.carlocatorapi.API.Retrofit


import android.util.Log
import neilsayok.github.carlocatorapi.API.Data.StatusData
import com.github.javafaker.Faker
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import neilsayok.github.carlocatorapi.API.ResponseCodes
import org.json.JSONArray
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class OTPTest {

    private lateinit var api: RetrofitAPI
    private lateinit var faker: Faker
    private lateinit var resCodes: Map<String,Int>

    var email: String? = null
    var pass : String?  = null
    var regID : String?  = null

    @Before
    fun setUp() {
        api = RetrofitBuilder().retrofit.create(
            RetrofitAPI::class.java)
        faker = Faker()

        resCodes = ResponseCodes().responses

        email = faker.internet().emailAddress()
        pass = faker.aquaTeenHungerForce().character()
        regID = faker.regexify("[A-Z][A-Z]\\s\\d\\d[a-zA-Z][a-zA-Z]\\s\\d\\d\\d\\d")

        val res = api.signup(email,regID,email,pass,pass).execute()


    }

    @After
    fun tearDown() {
        if (email != null){
            api.del_user_from_db(email).execute()
        }

    }

    @Test
    fun `Test Send OTP`(){
        val res = api.send_otp(email).execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["OTP_Mail_sent"])
    }

//    @Test
//    fun `Test Resend OTP`(){
//        val res = api.resend_otp(email).execute()
//        assertThat(res.body()!!.code).isEqualTo(resCodes["OTP_Mail_resent"])
//    }

    @Test
    fun `Test Send OTP Fail`(){
        val res = api.send_otp("test").execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["OTP_Mail_not_sent"])
    }

//    @Test
//    fun `Test Resend OTP Failed`(){
//        val res = api.resend_otp(email+"test").execute()
//        assertThat(res.body()!!.code).isEqualTo(resCodes["OTP_Mail_not_resent"])
//    }











}