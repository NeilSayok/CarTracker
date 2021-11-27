package com.example.neil.carlocatoruserside1m

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import neilsayok.github.carlocatorapi.API.Retrofit.RetrofitAPI
import neilsayok.github.carlocatorapi.API.Retrofit.RetrofitBuilder

class DefaultActivity: AppCompatActivity() {

    companion object{
        val api: RetrofitAPI by lazy { RetrofitBuilder().retrofit.create(RetrofitAPI::class.java) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)
    }



}