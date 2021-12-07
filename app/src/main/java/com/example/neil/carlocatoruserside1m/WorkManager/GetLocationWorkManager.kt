package com.example.neil.carlocatoruserside1m.WorkManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.neil.carlocatoruserside1m.ViewModels.SingleUserDataViewModel
import com.example.neil.carlocatoruserside1m.ViewModels.UserDataViewModel

class GetLocationWorkManager(appContext: Context, workerParams: WorkerParameters, val viewModel: SingleUserDataViewModel):
    Worker(appContext,workerParams) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}