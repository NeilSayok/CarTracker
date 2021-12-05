package com.example.neil.carlocatoruserside1m.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.neil.carlocatoruserside1m.DefaultActivity
import com.example.neil.carlocatoruserside1m.Room.UserData
import com.example.neil.carlocatoruserside1m.Room.UserDataRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SingleUserDataViewModel(application: Application,val id: Int): AndroidViewModel(application) {

    private val repo: UserDataRepo = UserDataRepo(DefaultActivity.userDAO)

    val userData: LiveData<UserData> = repo.getUser(id)

    fun updateUser(vararg userData: UserData){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateUserData(*userData)
        }
    }
}