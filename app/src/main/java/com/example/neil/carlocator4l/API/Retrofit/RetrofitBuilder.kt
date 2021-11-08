package com.example.neil.carlocator4l.API.Retrofit

import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.OkHttpClient




class RetrofitBuilder {

    lateinit var retrofit: Retrofit
    init {
        val logging = HttpLoggingInterceptor()
        logging.level = Level.BODY

        val baseUrl = "https://vehicle-locator.herokuapp.com/"

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()



        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            //.client(client)
            .build()

    }




}