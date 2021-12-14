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


class StatusGetterTest {

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
    fun `Test StausGetter id_stat_online`(){
        val res = api.statusGetter("rumadeymajumder0707@gmail.com").execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["id_stat_online"])
    }


    @Test
    fun `Test StausGetter id_statemail_empty`(){
        val res = api.statusGetter().execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["id_statemail_empty"])
    }

    @Test
    fun `Test StausGetter id_statstat_no_matching_email`(){
        val res = api.statusGetter("test").execute()
        assertThat(res.body()!!.code).isEqualTo(resCodes["id_statstat_no_matching_email"])
    }












}