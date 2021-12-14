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

class LoginTest {

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
    fun `Test Login Password Correct`(){
        val res = api.login("sdmsdm1998@gmail.com","Mypassword1#").execute()

        assertThat(res.body()!!.code).isEqualTo(resCodes["success"])
    }

    @Test
    fun `Test Login Password Inorrect`(){
        val res = api.login("sdmsdm1998@gmail.com","password1#").execute()

        assertThat(res.body()!!.code).isEqualTo(resCodes["passMiss"])
    }

    @Test
    fun `Test Login Send Empty Feilds - Password`(){
        val res = api.login("sdmsdm1998@gmail.com","").execute()

        assertThat(res.body()!!.code).isEqualTo(resCodes["Null_Value_Not_Allowed"])
    }

    @Test
    fun `Test Login Send Empty Feilds - Email`(){
        val res = api.login("","test").execute()

        assertThat(res.body()!!.code).isEqualTo(resCodes["Null_Value_Not_Allowed"])
    }

    @Test
    fun `Test Login wrong email`(){
        val res = api.login("dsmsdm@gmail.com","test").execute()

        assertThat(res.body()!!.code).isEqualTo(resCodes["credMiss"])
    }








}