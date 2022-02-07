package com.example.neil.carlocatoruserside1m.Room

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.javafaker.Faker
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class AppDBTest {

    private lateinit var appDB: AppDB
    private lateinit var appDao: UserDAO

    private lateinit var faker: Faker

    var name: String? = null
    var email: String? = null
    var pass : String?  = null
    var regID : String?  = null

    @Before
    fun setUp() {
        val mCtx = ApplicationProvider.getApplicationContext<Context>()
        faker = Faker()
        appDB = Room.inMemoryDatabaseBuilder(mCtx,AppDB::class.java).build()
        appDao = appDB.userDAO()

        name = faker.name().fullName()
        email = faker.internet().emailAddress()
        pass = faker.aquaTeenHungerForce().character()
        regID = faker.regexify("[A-Z][A-Z]\\s\\d\\d[a-zA-Z][a-zA-Z]\\s\\d\\d\\d\\d")

    }

    @After
    fun tearDown() {
        appDB.close()
    }

//    @Test
//    fun insertAllUserTest()= runBlocking {
//        val userData = UserData(0,name,email,regID,pass,true,)
//        appDao.insertAllUsers(userData)
//        val lifeCycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
//        lifeCycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
//
//        val item = appDao.getAllUsers().observe(lifeCycle)
//        assertThat(item.contains(userData)).isTrue()
//    }


}