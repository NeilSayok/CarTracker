package com.example.neil.carlocator4l.API.Retrofit


import android.util.Log
import com.example.neil.carlocator4l.API.Data.StatusData
import com.github.javafaker.Faker
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import org.json.JSONArray
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RetrofitBuilderTest {

    private lateinit var api: RetrofitAPI
    private lateinit var faker: Faker
    @Before
    fun setUp() {
        api = RetrofitBuilder().retrofit.create(RetrofitAPI::class.java)
        faker = Faker()
    }

    @After
    fun tearDown() {

    }


    @Test
    fun testCheckinBD(){
        val res = api.checkUserinDB("sdmsdm1998@gmail.com").execute()
        val r = res.body().toString()
        println(res.body().toString())
        assertThat(r).isNotNull()
    }

    @Test
    fun `StatusGetter with correct emails`(){
        val arr = arrayListOf("cloud.iot98@gmail.com","sdmsdm1998@gmail.com")
        val gson = Gson()
        val res: Response<StatusData> = api.statusGetter(gson.toJson(arr)).execute()
        val r: StatusData? = res.body()
        assertThat(r!!.data!!.size).isEqualTo(2)
    }

    @Test
    fun `StatusGetter with empty emails arr`(){
        val arr = arrayListOf("")
        val gson = Gson()
        val res: Response<StatusData> = api.statusGetter(gson.toJson(arr)).execute()
        val r: StatusData? = res.body()
        assertThat(r!!.data!!.size).isEqualTo(0)
    }

    @Test
    fun `StatusGetter with null emails`(){
        val res: Response<StatusData> = api.statusGetter(null).execute()
        val r: StatusData? = res.body()
        assertThat(r!!.data).isNull()
    }

    @Test
    fun `test faker`(){
        println(faker.regexify("[A-Z][A-Z] \\d\\d[A-Z][A-Z] \\d\\d\\d\\d"))
        assert(true)
    }

    @Test
    fun `Signup all fields correct`(){
        val pass = faker.animal().name()
        val res= api.signup(
            faker.name().fullName(),
            faker.regexify("[A-Z][A-Z] \\d\\d[A-Z][A-Z] \\d\\d\\d\\d"),
            faker.internet().emailAddress(),
            pass,
            pass).execute()

        assertThat(res.body()!!.response).isEqualTo("Account_Created")

    }

    @Test
    fun `Signup empty fields`(){
        val pass = faker.animal().name()
        val res= api.signup(
            faker.name().fullName(),
            faker.regexify("[A-Z][A-Z] \\d\\d[A-Z][A-Z] \\d\\d\\d\\d"),
            faker.internet().emailAddress(),
            "",
            pass).execute()

        assertThat(res.body()!!.response).isEqualTo("Null_Value_Restricted")

    }

    @Test
    fun `Signup null fields`(){
        val pass = faker.animal().name()
        val res= api.signup(
            null,
            faker.regexify("[A-Z][A-Z] \\d\\d[A-Z][A-Z] \\d\\d\\d\\d"),
            faker.internet().emailAddress(),
            pass,
            pass).execute()

        assertThat(res.body()!!.response).isEqualTo("Null_Value_Restricted")

    }

    @Test
    fun `Signup Password Mismatch`(){
        val res= api.signup(
            faker.name().fullName(),
            faker.regexify("[A-Z][A-Z] \\d\\d[A-Z][A-Z] \\d\\d\\d\\d"),
            faker.internet().emailAddress(),
            faker.animal().name(),
            faker.animal().name()).execute()

        assertThat(res.body()!!.response).isEqualTo("Password_Missmatch")

    }

    @Test
    fun `Signup Wrong Email Format`(){
        val pass = faker.animal().name()
        val res= api.signup(
            faker.name().fullName(),
            faker.regexify("[A-Z][A-Z] \\d\\d[A-Z][A-Z] \\d\\d\\d\\d"),
            faker.internet().macAddress(),
            pass,
            pass).execute()

        assertThat(res.body()!!.response).isEqualTo("Email_format_wrong")

    }

    @Test
    fun `Signup Email_Already_Pressent`(){
        val pass = faker.animal().name()
        val res= api.signup(
            faker.name().fullName(),
            faker.regexify("[A-Z][A-Z] \\d\\d[A-Z][A-Z] \\d\\d\\d\\d"),
            "sdmsdm1998@gmail.com",
            pass,
            pass).execute()

        assertThat(res.body()!!.response).isEqualTo("Email_Already_Pressent")

    }

    @Test
    fun `Signup Account_Not_Success`(){
        //Test will return wrong ans
        val res= api.signup(
            "Avijit",
            "JK 1027192",
            "avijit.majumder1960@gmail.com",
            "12345@avi",
            "12345@avi").execute()

            assert(true)

    }








}