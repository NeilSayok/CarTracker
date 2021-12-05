package com.example.neil.carlocatoruserside1m.ViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SingleUSerDataVMFactory(val application: Application, val id: Int): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SingleUserDataViewModel(application = application, id = id) as T
    }
}