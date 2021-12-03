package com.example.neil.carlocatoruserside1m

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.neil.carlocatoruserside1m.Room.AppDB
import com.example.neil.carlocatoruserside1m.Room.UserDAO
import neilsayok.github.carlocatorapi.API.ResponseCodes
import neilsayok.github.carlocatorapi.API.Retrofit.RetrofitAPI
import neilsayok.github.carlocatorapi.API.Retrofit.RetrofitBuilder

class DefaultActivity: AppCompatActivity() {

    companion object{
        val resCodes = ResponseCodes().responses

        val api: RetrofitAPI by lazy { RetrofitBuilder().retrofit.create(RetrofitAPI::class.java) }
        lateinit var appDB: AppDB
        lateinit var userDAO: UserDAO

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        appDB = AppDB.getInstance(applicationContext)
        userDAO = appDB.userDAO()
    }



}