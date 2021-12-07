package com.example.neil.carlocator4l.Fragments

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.neil.carlocator4l.DefaultActivity
import com.example.neil.carlocator4l.PermissionHandler.EasyPermissionsHasPermissions
import com.example.neil.carlocator4l.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import neilsayok.github.carlocatorapi.API.Data.CheckInDbData
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashFragment: Fragment() , EasyPermissions.PermissionCallbacks{

    private val RC_LOC_PERM: Int = 101
    private var vGroup: ViewGroup? = null
    private lateinit var easyPermHandler: EasyPermissionsHasPermissions
    private lateinit var v: View
    private lateinit var sp: SharedPreferences
    private var email: String? = null
    private var vhe_id: String? = null
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sp = requireContext().getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        email = sp.getString("email", "")
        vhe_id = sp.getString("veh_id", "")

        navController = findNavController()

        vGroup = container

        v = inflater.inflate(R.layout.activity_splash, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        easyPermHandler = EasyPermissionsHasPermissions(requireContext())
        requesrtPermissions()


    }

    fun checkinDB(){
        //val api = RetrofitBuilder().retrofit.create(RetrofitAPI::class.java)

        val call: Call<CheckInDbData> = DefaultActivity.api.checkUserinDB(email,vhe_id)

        call.enqueue(object : Callback<CheckInDbData?> {
            override fun onResponse(
                call: Call<CheckInDbData?>,
                response: Response<CheckInDbData?>
            ) {
                val res = response.body()
                Log.d("Response", res.toString())
                if (res!!.present) {
                    sp.edit().putBoolean("verified", res.verified != "0").apply()
                    sp.edit().putString("name", res.name).apply()
                    sp.edit().putString("reg_id", res.regId).apply()
                    sp.edit().putString("email", res.email).apply()

                    if (res.verified == "0"){
                        navController.navigate(R.id.action_splashFragment_to_OTPFragment)
                    }else{
                        navController.navigate(R.id.action_splashFragment_to_trackFragment)
                    }



                }
                else{
                    sp.edit().remove("email").apply()
                    sp.edit().remove("name").apply()
                    sp.edit().remove("reg_id").apply()
                    sp.edit().remove("verified").apply()
                    navController.navigate(R.id.action_splashFragment_to_signinFragment)

                }

            }

            override fun onFailure(call: Call<CheckInDbData?>, t: Throwable) {
                Snackbar.make(v,"Please check your internet connection",Snackbar.LENGTH_SHORT).show()
            }

        })
    }

    fun requesrtPermissions(){

        if (easyPermHandler.hasLocationPermission() && easyPermHandler.hasForeGroundServicePermission()) {
            Log.d("Request", "Permissions present")

            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            Log.d("Request", "Permissions Q")

            EasyPermissions.requestPermissions(
                this,
                "You need to accept permission to use this app",
                RC_LOC_PERM,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WAKE_LOCK

            )
        }else{
            Log.d("Request", "Permissions")

            EasyPermissions.requestPermissions(
                this,
                "You need to accept permission to use this app",
                RC_LOC_PERM,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION ,
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.WAKE_LOCK
            )



        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d("Perms", perms.toString())

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){

        }else{

        }

        if(email.isNullOrBlank() && vhe_id.isNullOrEmpty()){
            navController.navigate(R.id.action_splashFragment_to_signinFragment)
        }else{
            lifecycleScope.launch {
                checkinDB()
            }

        }

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            //Log.d("Dialog:","Displayed Form Here")
            val builder = AlertDialog.Builder(requireContext())
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_request_location_permission,vGroup,false)
            val openSettingsBtn = dialogView.findViewById<Button>(R.id.give_perm_btn)
            builder.setView(dialogView)
            val dialog = builder.create()

            openSettingsBtn.setOnClickListener {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
                intent.data = uri
                startActivity(intent)
                dialog.dismiss()
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                try {
                    val backgroundLocationPermissionApproved = ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION) > -1
                    //Log.d("TestPErm",backgroundLocationPermissionApproved.toString())
                    if (!backgroundLocationPermissionApproved){
                        val bodyText = dialogView.findViewById<TextView>(R.id.perm_body_text_tv)
                        bodyText.text = "Please Select \"Allow all the time.\" so for the app to work properly."

                    }

                }catch (e:Exception){
                    e.printStackTrace()
                }

            dialog.show()
        }else{
            requesrtPermissions()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)

    }


}