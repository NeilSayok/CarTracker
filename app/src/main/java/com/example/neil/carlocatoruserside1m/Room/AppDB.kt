package com.example.neil.carlocatoruserside1m.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import neilsayok.github.carlocatorapi.API.ResponseCodes

@Database(entities = [UserData::class], version = 1)
abstract class AppDB: RoomDatabase() {


    companion object{
        private var instance: AppDB? = null

        fun getInstance(ctx: Context): AppDB{
            if (instance == null){
                synchronized(AppDB::class){
                    instance = Room.databaseBuilder(ctx.applicationContext,
                        AppDB::class.java,"AppDatabase")
                        .fallbackToDestructiveMigration()
                        .build()
                }

            }
            return instance!!
        }

    }

    abstract fun userDAO():UserDAO



}