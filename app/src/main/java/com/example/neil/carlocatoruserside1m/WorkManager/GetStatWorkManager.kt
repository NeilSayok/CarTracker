package com.example.neil.carlocatoruserside1m.WorkManager

import android.content.Context
import android.util.Log
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

class GetStatWorkManager(appContext: Context, workerParams: WorkerParameters,val viewModel: UserDataViewModel):Worker(appContext,workerParams) {

    override fun doWork(): Result {

        var resp = Result.failure()

        val emails = viewModel.emails.value?.joinToString(",")

        if (emails.isNullOrBlank()){
            resp = Result.success()
        }
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
                resp = Result.success()

            }

            override fun onFailure(call: Call<StatusData>, t: Throwable) {
                Log.d("Error", t.toString())
            }

        })

        return Result.success()
    }
}