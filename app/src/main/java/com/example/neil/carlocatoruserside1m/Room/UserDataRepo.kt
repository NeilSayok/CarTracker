package com.example.neil.carlocatoruserside1m.Room

import androidx.lifecycle.LiveData

class UserDataRepo(private val userDao : UserDAO) {

    val getAllUser: LiveData<List<UserData>> = userDao.getAllUsers()
    fun getUser(id: Int): UserData = userDao.getUser(id)
    val getAllEmail:  LiveData<List<String>> = userDao.getAllEmails()


    fun insertUserData(vararg userData: UserData){
        userDao.insertAllUsers(*userData)
    }

    fun updateUserData(vararg userData: UserData){
        userDao.updateUsers(*userData)
    }

    fun deleteUser(vararg userData: UserData){
        userDao.delete(*userData)
    }
    fun deleteAllData(){
        userDao.deleteAll()
    }

}