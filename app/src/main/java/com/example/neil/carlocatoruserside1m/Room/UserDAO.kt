package com.example.neil.carlocatoruserside1m.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import neilsayok.github.carlocatorapi.API.Data.Data

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(vararg users: UserData)

    @Update(onConflict  = OnConflictStrategy.REPLACE)
    fun updateUsers(vararg user: UserData)


    @Query("SELECT * FROM user_details")
    fun getAllUsers():  LiveData<List<UserData>>

    @Query("SELECT email FROM user_details")
    fun getAllEmails():  LiveData<List<String>>

    @Query("SELECT * FROM user_details WHERE id = :userId LIMIT 1")
    fun getUser(userId: Int): UserData


    @Delete
    fun delete(vararg user: UserData)

    @Query("DELETE FROM user_details")
    fun deleteAll()
}