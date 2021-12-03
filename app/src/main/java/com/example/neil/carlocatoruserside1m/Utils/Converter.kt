package com.example.neil.carlocatoruserside1m.Utils

import androidx.room.ColumnInfo
import com.example.neil.carlocatoruserside1m.Room.UserData
import neilsayok.github.carlocatorapi.API.Data.Data

class Converter {
    
    fun convertDataToUserData(data: Data): UserData = UserData(
        id = data.id!!,
        name = data.name,
        email = data.email,
        regId = data.regId,
        password = data.password,
        verified = data.verified,
        latitude = data.latitude as Double,
        longitude = data.longitude as Double,
        time = data.time,
        logStat = data.logStat,
    )
    
}