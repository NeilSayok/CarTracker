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

){
    fun UserData.fromRetrofitToRoom(data: Data){
        this.id = data.id!!
        this.name = data.name
        this.email = data.email
        this.regId = data.regId
        this.password = data.password
        this.verified = data.verified
        this.latitude = data.latitude as Double
        this.longitude = data.longitude as Double
        this.time = data.time
        this.logStat = data.logStat

    }
}