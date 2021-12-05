package com.example.neil.carlocatoruserside1m.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import neilsayok.github.carlocatorapi.API.Data.Data

@Entity(tableName = "user_details")
data class UserData(
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "email")
    var email: String? = null,
    @ColumnInfo(name = "reg_id")
    var regId: String? = null,
    @ColumnInfo(name = "password")
    var password: String? = null,
    @ColumnInfo(name = "verified")
    var verified: Boolean? = null,
    @ColumnInfo(name = "latitude")
    var latitude: Double? = null,
    @ColumnInfo(name = "longitude")
    var longitude: Double? = null,
    @ColumnInfo(name = "time")
    var time: Long? = null,
    @ColumnInfo(name = "log_stat")
    var logStat: Boolean = false

)