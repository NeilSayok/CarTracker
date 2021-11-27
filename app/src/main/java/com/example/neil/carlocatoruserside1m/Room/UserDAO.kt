package com.example.neil.carlocatoruserside1m.Room

import androidx.room.*

@Dao
interface UserDAO {

    @Query("SELECT * FROM UserData")
    fun getAll(): List<UserData>

    @Query("SELECT * FROM UserData WHERE id = :userId LIMIT 1")
    fun getUser(userId: Int): UserData

    @Update(onConflict  = OnConflictStrategy.REPLACE)
    fun updateUser(user:UserData)

    @Insert
    fun insertAll(vararg users: UserData)

    @Delete
    fun delete(user: UserData)
}