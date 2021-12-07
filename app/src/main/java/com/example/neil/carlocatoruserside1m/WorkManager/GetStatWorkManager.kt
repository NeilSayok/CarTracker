package com.example.neil.carlocatoruserside1m.WorkManager

import android.content.Context
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.neil.carlocatoruserside1m.DefaultActivity
import com.example.neil.carlocatoruserside1m.R
import com.example.neil.carlocatoruserside1m.Utils.Converter
import com.example.neil.carlocatoruserside1m.ViewModels.UserDataViewModel
import neilsayok.github.carlocatorapi.API.Data.StatusData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetStatWorkManager(appContext: Context, workerParams: WorkerParameters):Worker(appContext,workerParams) {

    override fun doWork(): Result {

        val viewModel = DefaultActivity.userViewModel


        val emails = viewModel.emails.value?.joinToString(",")

//        viewModel.emails.observe(this, Observer {
//
//        })

        if (emails.isNullOrBlank()){
            Log.d("Emails","Empty")
        }else{
            Log.d("Emails","$emails")

            val call = DefaultActivity.api.statusGetter(emails)
            call.enqueue(object : Callback<StatusData> {
                override fun onResponse(call: Call<StatusData>, response: Response<StatusData>) {
                    val res = response.body()
                    Log.d("work","Retrofit $res")

                    when (res!!.code){
                        DefaultActivity.resCodes["id_stat_online"]->{

                            for(d in res.data!!)
                                viewModel.updateUser(Converter().convertDataToUserData(d))
                        }

                    }

                }

                override fun onFailure(call: Call<StatusData>, t: Throwable) {
                    Log.d("Error", t.toString())
                }

            })
        }




        return Result.success()
    }
}