package com.example.neil.carlocatoruserside1m.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "email")
    val email: String? = null,
    @ColumnInfo(name = "reg_id")
    val regId: String? = null,
    @ColumnInfo(name = "password")
    val password: String? = null,
    @ColumnInfo(name = "verified")
    val verified: Boolean? = null,
    @ColumnInfo(name = "latitude")
    val latitude: Any? = null,
    @ColumnInfo(name = "longitude")
    val longitude: Any? = null,
    @ColumnInfo(name = "time")
    val time: Long? = null,
    @ColumnInfo(name = "log_stat")
    val logStat: Boolean = false

)