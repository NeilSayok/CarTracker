package com.example.neil.carlocator4l.Fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.neil.carlocator4l.PermissionHandler.EasyPermissionsHasPermissions
import com.example.neil.carlocator4l.R
import pub.devrel.easypermissions.EasyPermissions

class SplashFragment: Fragment() , EasyPermissions.PermissionCallbacks{

    private val RC_LOC_PERM: Int = 101
    private var vGroup: ViewGroup? = null
    private lateinit var easyPermHandler: EasyPermissionsHasPermissions
    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vGroup = container
        v = inflater.inflate(R.layout.activity_splash, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        easyPermHandler = EasyPermissionsHasPermissions(requireContext())
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