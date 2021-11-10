package com.example.neil.carlocator4l

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.neil.carlocator4l.PermissionHandler.EasyPermissionsHasPermissions
import pub.devrel.easypermissions.EasyPermissions


class SplashActivity: AppCompatActivity(), EasyPermissions.PermissionCallbacks{

    private val RC_LOC_PERM: Int = 101

    private lateinit var vGroup: ViewGroup

    private lateinit var easyPermHandler: EasyPermissionsHasPermissions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        easyPermHandler = EasyPermissionsHasPermissions(applicationContext)
        vGroup = findViewById(R.id.root)
        requesrtPermissions()

    }


    fun requesrtPermissions(){
        if (easyPermHandler.hasLocationPermission())
            return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            EasyPermissions.requestPermissions(
                this,
                "You need to accept permission to use this app",
                RC_LOC_PERM,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }else{
            EasyPermissions.requestPermissions(
                this,
                "You need to accept permission to use this app",
                RC_LOC_PERM,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )



        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            //Log.d("Dialog:","Displayed Form Here")
            val builder = AlertDialog.Builder(this)
            val dialogView = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_request_location_permission,vGroup,false)
            val openSettingsBtn = dialogView.findViewById<Button>(R.id.give_perm_btn)
            builder.setView(dialogView)
            val dialog = builder.create()

            openSettingsBtn.setOnClickListener {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", applicationContext.packageName, null)
                intent.data = uri
                startActivity(intent)
                dialog.dismiss()
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                try {
                    val backgroundLocationPermissionApproved = ActivityCompat.checkSelfPermission(applicationContext,
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