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


class SignUpTest {

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


    }

    @After
    fun tearDown() {
        if (email != null){
            api.del_user_from_db(email).execute()
        }
    }

    @Test
    fun `Test Signup Account_Created`(){
        println("$email, $regID, $pass")
        val res = api.signup(email,regID,email,pass,pass).execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["Account_Created"])
    }

    @Test
    fun `Test Signup Null_Value_Restricted`(){
        println("$email, $regID, $pass")
        val res = api.signup(email,regID,email,pass,).execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["Null_Value_Restricted"])
    }

    @Test
    fun `Test Signup Password_Missmatch`(){
        println("$email, $regID, $pass")
        val res = api.signup(email,regID,email,pass,faker.aquaTeenHungerForce().character()).execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["Password_Missmatch"])
    }

    @Test
    fun `Test Signup Email_format_wrong`(){
        println("$email, $regID, $pass")
        val res = api.signup(email,regID,"test",pass,pass).execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["Email_format_wrong"])
    }

    @Test
    fun `Test Signup Email_Already_Pressent`(){
        println("$email, $regID, $pass")
        val res = api.signup(email,regID,"sdmsdm1998@gmail.com",pass,pass).execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["Email_Already_Pressent"])
    }

//    @Test
//    fun `Test Signup Account_Not_Success`(){
//        println("$email, $regID, $pass")
//        val res = api.signup(email,regID,email,pass,pass).execute()
//        assertThat(res.body()!!.code).isEqualTo(resCodes["Account_Not_Success"])
//    }



}