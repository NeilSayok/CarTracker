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


class VerifyOTPTest {

    private lateinit var api: RetrofitAPI
    private lateinit var faker: Faker
    private lateinit var resCodes: Map<String,Int>



    @Before
    fun setUp() {
        api = RetrofitBuilder().retrofit.create(
            RetrofitAPI::class.java)
        faker = Faker()

        resCodes = ResponseCodes().responses


    }

    @After
    fun tearDown() {

    }

    @Test
    fun `Test verify otp pass`(){
        val res = api.verify_otp("888406","marcus.mcdermott@gmail.com").execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["stat_otp_verified"])
    }

    @Test
    fun `Test verify otp fail`(){
        val res = api.verify_otp("271911","avijit.majumder1960@gmail.comtest").execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["stat_otp_not_verified"])
    }













}