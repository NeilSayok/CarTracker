package com.example.neil.carlocator4l

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.neil.carlocator4l.API.Retrofit.RetrofitAPI
import com.example.neil.carlocator4l.API.Retrofit.RetrofitBuilder
import com.example.neil.carlocator4l.PermissionHandler.EasyPermissionsHasPermissions
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class SplashActivity: AppCompatActivity(),EasyPermissions.PermissionCallbacks,
EasyPermissions.RationaleCallbacks {
    private val RC_VIBRATE_PERM: Int = 103
    private val RC_INTERNET_PERM: Int = 102
    private val RC_FINE_LOC_PERM: Int = 101
    private val RC_COARSE_LOC_PERM: Int = 100

    private lateinit var easyPermHandler: EasyPermissionsHasPermissions


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_splash)
        easyPermHandler = EasyPermissionsHasPermissions(applicationContext)
        checkforPermission()
    }

    suspend fun checkUserInDB(emial: String){
        val api = RetrofitBuilder().retrofit.create(RetrofitAPI::class.java)

        //api.checkUserinDB().enqueue()
    }

    fun checkforPermission(){

        if (!easyPermHandler.hasCoarseLocationPermission()){

            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_coarse_location),
                RC_COARSE_LOC_PERM,
                Manifest.permission.CAMERA)

        }
        if (!easyPermHandler.hasFineLocationPermission()){
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_coarse_location),
                RC_FINE_LOC_PERM,
                Manifest.permission.CAMERA)

        }
        if (!easyPermHandler.hasInternetPermission()){
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_internet_location),
                RC_INTERNET_PERM,
                Manifest.permission.CAMERA)

        }
        if (!easyPermHandler.hasVibratePermission()){
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_vibrate_location),
                RC_VIBRATE_PERM,
                Manifest.permission.CAMERA)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d("onPermissionsGranted", "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms))
        {
            AppSettingsDialog.Builder(this).build().show()

        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d("onRationaleAccepted", "onRationaleAccepted:$requestCode")
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d("onRationaleAccepted", "onRationaleDenied:$requestCode")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE)
        {
            val yes = getString(R.string.yes)
            val no = getString(R.string.no)
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                this,
                getString(R.string.returned_from_app_settings_to_activity,
                    if (easyPermHandler.hasCoarseLocationPermission()) yes else no,
                    if (easyPermHandler.hasFineLocationPermission()) yes else no,
                    if (easyPermHandler.hasInternetPermission()) yes else no,
                    if (easyPermHandler.hasVibratePermission()) yes else no),
            Toast.LENGTH_LONG).show()
        }
    }

}