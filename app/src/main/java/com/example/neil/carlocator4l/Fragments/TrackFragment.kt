package com.example.neil.carlocator4l.Fragments

import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import com.example.neil.carlocator4l.DefaultActivity
import com.example.neil.carlocator4l.Services.GPSService
import com.example.neil.carlocator4l.R
import com.example.neil.carlocator4l.Services.LocationService
import com.google.android.material.snackbar.Snackbar
import neilsayok.github.carlocatorapi.API.Data.CheckLogStatData
import neilsayok.github.carlocatorapi.API.Data.SimpleResponseData
import neilsayok.github.carlocatorapi.API.ResponseCodes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TrackFragment : Fragment() {

    private lateinit var v: View

    private lateinit var i: Intent
    private lateinit var serviceIntent:Intent
    private lateinit var base: RelativeLayout
    private lateinit var delUserUrl: String
    private lateinit var sp: SharedPreferences
    var broadcastReceiver: BroadcastReceiver? = null
    private lateinit var lat: TextView
    private lateinit var longi:TextView
    private lateinit var time:TextView
    private lateinit var track: Button
    private lateinit var progressLayout: ViewGroup
    var actionbar: ActionBar? = null




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.activity_tracking, container, false)
        setHasOptionsMenu(true)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerReciever()

        sp = requireContext().getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        serviceIntent = Intent(requireContext(), LocationService::class.java)

        actionbar = (requireActivity() as AppCompatActivity?)!!.supportActionBar
        actionbar?.show()
        actionbar?.setTitle("Car Locator App")




        base = v.findViewById(R.id.baseLayout)
        lat = v.findViewById(R.id.lat)
        longi = v.findViewById(R.id.longi)
        time = v.findViewById(R.id.time)
        track = v.findViewById(R.id.startstopTrackingService)
        progressLayout = v.findViewById(R.id.progressLayout)
        
        if (sp.getBoolean("serviceStat", false)) {
            track.background = getDrawable(requireContext(),R.drawable.rect_btn_red)
            track.text = "Stop tracking your car"
        }

        track.setOnClickListener(onClickListener)


    }




    val resCodes = ResponseCodes().responses

    private val onClickListener =
        View.OnClickListener { v ->
            if (v.id == R.id.startstopTrackingService) {
                progressLayout.visibility = View.VISIBLE
                //Check if service is running or not.
                if (!sp.getBoolean("serviceStat", false)) {
                    //Checklogstat
                    val call : Call<CheckLogStatData> = DefaultActivity.api.check_log_stat(sp.getString("email",""))
                    call.enqueue(object : Callback<CheckLogStatData>{
                        override fun onResponse(
                            call: Call<CheckLogStatData>,
                            response: Response<CheckLogStatData>
                        ) {
                            progressLayout.visibility = View.GONE
                            val res = response.body()
                            Log.d("check_log_stat",res.toString())

                            when(res!!.code){
                                resCodes["check_log_stat_success"]-> if (res.stat == 0){
                                    startService()
                                    track.background = getDrawable(requireContext(),R.drawable.rect_btn_red)
                                    track.text = "Stop tracking your car"
                                    Log.d("Test", "1")
                                    sp.edit().putBoolean("serviceStat", true).apply()
                                }else{
                                    stopService()
                                    Snackbar.make(v,"Log out of this account from other devices",
                                        Snackbar.LENGTH_LONG)
                                        .setAction("OK") { }.show()
                                }

                                else->{
                                    stopService()
                                    Snackbar.make(v,"Something went wrong.",Snackbar.LENGTH_LONG).show()}
                            }
                        }

                        override fun onFailure(call: Call<CheckLogStatData>, t: Throwable) {
                            progressLayout.visibility = View.GONE
                            Snackbar.make(v,"Opps, Something went Wrong.Please Try again.",Snackbar.LENGTH_LONG).show()                        }

                    })

                } else {

                    //If Service is already running stop the service after setting tte stat to 0
                    val call: Call<SimpleResponseData> = DefaultActivity.api.write_log_stat(
                        sp.getString("email",""),
                        0
                    )

                    call.enqueue(object: Callback<SimpleResponseData>{
                        override fun onResponse(
                            call: Call<SimpleResponseData>,
                            response: Response<SimpleResponseData>
                        ) {
                            progressLayout.visibility = View.GONE
                            sp.edit().putBoolean("serviceStat", false).apply()
                            track.background = getDrawable(requireContext(),R.drawable.rect_btn_green)
                            track.text = "Start tracking your car"
                            stopService()                        }

                        override fun onFailure(call: Call<SimpleResponseData>, t: Throwable) {
                            progressLayout.visibility = View.GONE
                            Snackbar.make(v,"Opps, Something went Wrong.Please Try again.",Snackbar.LENGTH_LONG).show()
                        }

                    })
                }
            }
        }

    private fun registerReciever(){
        if (DefaultActivity.broadcastReceiver == null) {
            DefaultActivity.broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    longi.text = "Longitude : " + intent.extras!!["longitude"]
                    lat.text = "Latitude : " + intent.extras!!["latitude"]
                    time.text = "Your Location at : " + intent.extras!!["time"]
                }
            }
        }
        requireActivity().registerReceiver(DefaultActivity.broadcastReceiver, IntentFilter("location_update"))
    }

    override fun onResume() {
        super.onResume()
        registerReciever()
    }

    private fun startService(){
        requireContext().startService(serviceIntent)
    }

    private fun stopService(){
        requireContext().stopService(serviceIntent)
    }


}