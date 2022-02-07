package com.example.neil.carlocatoruserside1m.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.neil.carlocatoruserside1m.DefaultActivity
import com.example.neil.carlocatoruserside1m.R
import com.example.neil.carlocatoruserside1m.Room.UserData
import com.example.neil.carlocatoruserside1m.Utils.Converter
import com.example.neil.carlocatoruserside1m.ViewModels.UserDataViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import neilsayok.github.carlocatorapi.API.Data.Data
import neilsayok.github.carlocatorapi.API.Data.StatusData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashFragment: Fragment() {

    private lateinit var v: View
    private lateinit var navController: NavController
    private lateinit var viewModel : UserDataViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.activity_splash, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]


        val lifecycleOwner: LifecycleOwner = this


            viewModel.emails.observe(lifecycleOwner, Observer {
                if (it.isEmpty()){
                    Log.d("work","data Empty")

                    navController.navigate(R.id.action_splashFragment_to_mainFragment)
                }else{
                    lifecycleScope.launch(Dispatchers.IO) {
                        Log.d("work","Data Is Present")

                        val call = DefaultActivity.api.statusGetter(it.joinToString(","))
                        call.enqueue(object : Callback<StatusData>{
                            override fun onResponse(
                                call: Call<StatusData>,
                                response: Response<StatusData>
                            ) {
                                val res = response.body()
                                Log.d("work","Retrofit $res")

                                when (res!!.code){
                                    DefaultActivity.resCodes["id_stat_online"]->{

                                        for(d in res.data!!)
                                            viewModel.updateUser(Converter().convertDataToUserData(d))
                                        if(navController.currentDestination?.id == R.id.splashFragment){
                                            navController.navigate(R.id.action_splashFragment_to_mainFragment)
                                        }
                                    }

                                    else->{
                                        viewModel.deleteAll()
                                        Log.d("Current Destination" ,"${navController.currentDestination?.id}")
                                        if(navController.currentDestination?.id == R.id.splashFragment){
                                            navController.navigate(R.id.action_splashFragment_to_mainFragment)
                                        }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<StatusData>, t: Throwable) {
                                Snackbar.make(v,"Something went wrong. Please Try Again.",Snackbar.LENGTH_LONG).show()
                            }

                        })
                    }
                }
            })



    }






}