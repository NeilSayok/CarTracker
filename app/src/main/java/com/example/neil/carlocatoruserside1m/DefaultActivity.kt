package com.example.neil.carlocatoruserside1m

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.PeriodicWorkRequest
import com.example.neil.carlocatoruserside1m.Room.AppDB
import com.example.neil.carlocatoruserside1m.Room.UserDAO
import com.example.neil.carlocatoruserside1m.Utils.Converter
import com.example.neil.carlocatoruserside1m.ViewModels.UserDataViewModel
import kotlinx.coroutines.*
import neilsayok.github.carlocatorapi.API.Data.StatusData
import neilsayok.github.carlocatorapi.API.ResponseCodes
import neilsayok.github.carlocatorapi.API.Retrofit.RetrofitAPI
import neilsayok.github.carlocatorapi.API.Retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime


class DefaultActivity: AppCompatActivity() {

    private lateinit var statGetterWork: PeriodicWorkRequest
    var runJob = true
    var job: Job? = null
    private lateinit var call: Call<StatusData>
    private lateinit var emails: String




    companion object{
        val resCodes = ResponseCodes().responses

        val api: RetrofitAPI by lazy { RetrofitBuilder().retrofit.create(RetrofitAPI::class.java) }
        lateinit var appDB: AppDB
        lateinit var userDAO: UserDAO
        lateinit var userViewModel : UserDataViewModel

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        appDB = AppDB.getInstance(applicationContext)
        userDAO = appDB.userDAO()
        userViewModel = ViewModelProvider(this)[UserDataViewModel::class.java]

        emails = ""

        Log.d("Package Name", applicationContext.packageName)


    }

    override fun onResume() {
        super.onResume()
        userViewModel.emails.observe(this, Observer {
            emails = it.joinToString(",")
        })

        if (job == null)
            job = startRepeatingJob(5000L)
    }

    override fun onDestroy() {
        super.onDestroy()
        runJob = false
        if (job != null){
            job?.cancel()
            job = null
        }
    }

    override fun onPause() {
        super.onPause()
        runJob = false
        if (job != null){
            job?.cancel()
            job = null
        }
    }

    var i = 0
    var prev  = 0L


    private fun startRepeatingJob(timeInterval: Long): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                var curr = System.currentTimeMillis()
                Log.d("Test", "${(curr-prev)/1000}")
                callApi()
                prev = curr
                delay(timeInterval)
            }
        }
    }




    var pre = 0L
    suspend fun callApi(){
        if (!emails.isNullOrBlank()){
            call = api.statusGetter(emails)
            call.enqueue(object : Callback<StatusData> {
                override fun onResponse(call: Call<StatusData>, response: Response<StatusData>) {

                    val res = response.body()
                    Log.d("Retrofit ${(System.currentTimeMillis()-pre)/1000f}","Retrofit ${res}")
                    pre = System.currentTimeMillis()

                    when (res!!.code){
                        resCodes["id_stat_online"]->{
                            for(d in res.data!!)
                                userViewModel.updateUser(Converter().convertDataToUserData(d))
                        }

                    }

                }

                override fun onFailure(call: Call<StatusData>, t: Throwable) {
                    Log.d("Error", t.toString())
                }

            })
        }

    }









}