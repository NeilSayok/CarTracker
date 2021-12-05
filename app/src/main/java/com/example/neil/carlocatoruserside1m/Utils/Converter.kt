package com.example.neil.carlocatoruserside1m.Utils

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
        verified = data.verified,
        latitude = data.latitude?.toDoubleOrNull(),
        longitude = data.longitude?.toDoubleOrNull(),
        time = data.time,
        logStat = data.logStat,
    )

    fun convertLoginDataToUserData(data: LoginData) : UserData = UserData(
        id = data.id!!,
        name = data.name,
        email = data.email,
        regId = data.regId,
        verified = data.verified == "1",

    )
    
}