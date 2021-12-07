package com.example.neil.carlocator4l

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import neilsayok.github.carlocatorapi.API.Data.SimpleResponseData
import neilsayok.github.carlocatorapi.API.Retrofit.RetrofitAPI
import neilsayok.github.carlocatorapi.API.Retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DefaultActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var i: Intent

    private lateinit var sp: SharedPreferences

    private lateinit var base: ViewGroup

    //Static Objects
    companion object{
        var broadcastReceiver: BroadcastReceiver? = null
        val api: RetrofitAPI by lazy { RetrofitBuilder().retrofit.create(RetrofitAPI::class.java) }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        sp = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)


        toolbar = findViewById(R.id.mainToolbar)
        base = findViewById(R.id.base)

        toolbar.title = "Car Locator App"
        toolbar.setTitleTextColor(ResourcesCompat.getColor(resources,R.color.white,null));


        setSupportActionBar(toolbar)
        supportActionBar?.hide()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                Snackbar.make(base,"Do you want to logout?",Snackbar.LENGTH_LONG)
                    .setAction("Yes", View.OnClickListener {
                        i = Intent(this,DefaultActivity::class.java)
                        sp.edit().remove("email").apply()
                        sp.edit().remove("name").apply()
                        sp.edit().remove("reg_id").apply()
                        sp.edit().remove("password").apply()
                        sp.edit().remove("verified").apply()
                        sp.edit().remove("serviceStat").apply()
                        startActivity(i)
                        finish()
                    }).show()
            }
            R.id.delusr -> {
                i = Intent(this,DefaultActivity::class.java)
                val snackbar = Snackbar.make(
                    base,
                    "This will remove you from our database too...",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("OK I UNDERSTAND") {
                        //TODO Retrofit Api Call to del user from DB
                        api.del_user_from_db(sp.getString("email","")).enqueue(
                            object : Callback<SimpleResponseData>{
                                override fun onResponse(
                                    call: Call<SimpleResponseData>,
                                    response: Response<SimpleResponseData>
                                ) {
                                    Toast.makeText(applicationContext,"USer has been deleted from databas.",Toast.LENGTH_LONG)
                                        .show()
                                }

                                override fun onFailure(
                                    call: Call<SimpleResponseData>,
                                    t: Throwable
                                ) {
                                    Toast.makeText(applicationContext,"Please try again",Toast.LENGTH_SHORT).show()
                                }

                            }
                        )
                    }
                snackbar.show()
            }
            else -> {}
        }
        return true
    }



    override fun onDestroy() {
        super.onDestroy()
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver)
        }
    }



}