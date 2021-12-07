package com.example.neil.carlocatoruserside1m.Utils

import android.util.Log
import com.example.neil.carlocatoruserside1m.Room.UserData
import neilsayok.github.carlocatorapi.API.Data.Data
import neilsayok.github.carlocatorapi.API.Data.LoginData

class Converter {

    fun convertDataToUserData(data: Data): UserData = UserData(
            id = data.id!!,
            name = data.name,
            email = data.email,
            regId = data.regId,
            password = data.password,
            verified = data.verified == 1,
            latitude = data.latitude?.toDoubleOrNull(),
            longitude = data.longitude?.toDoubleOrNull(),
            time = data.time,
            logStat = data.logStat == 1,
        )


    fun convertLoginDataToUserData(data: LoginData) : UserData = UserData(
        id = data.id!!,
        name = data.name,
        email = data.email,
        regId = data.regId,
        verified = data.verified == "1",

    )
    
}