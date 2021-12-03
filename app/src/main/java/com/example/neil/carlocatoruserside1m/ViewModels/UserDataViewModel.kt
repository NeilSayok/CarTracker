package com.example.neil.carlocatoruserside1m.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

import com.example.neil.carlocatoruserside1m.DefaultActivity
import com.example.neil.carlocatoruserside1m.Room.UserData
import com.example.neil.carlocatoruserside1m.Room.UserDataRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDataViewModel(application: Application): AndroidViewModel(application) {

    val userData : LiveData<List<UserData>>
    val emails: LiveData<List<String>>
    private val repo: UserDataRepo

    init{
        repo = UserDataRepo(DefaultActivity.userDAO)
        userData = repo.getAllUser
        emails = repo.getAllEmail
        Log.d("Work","From INIT viewModel")
    }

    fun addUser(vararg user: UserData){
        viewModelScope.launch(Dispatchers.IO){
            repo.insertUserData(*user)
        }
    }

    fun updateUser(vararg user:UserData){
        viewModelScope.launch(Dispatchers.IO){
            repo.updateUserData(*user)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteAllData()
        }
    }





}