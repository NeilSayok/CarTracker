package com.example.neil.carlocator4l.PermissionHandler

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

class EasyPermissionsHasPermissions(val mctx: Context) {

     fun hasInternetPermission():Boolean {
        return EasyPermissions.hasPermissions(mctx, Manifest.permission.INTERNET)
    }
     fun hasVibratePermission():Boolean {
        return EasyPermissions.hasPermissions(mctx, Manifest.permission.VIBRATE)
    }
    fun hasLocationPermission() =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            EasyPermissions.hasPermissions(
                mctx,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }else{
            EasyPermissions.hasPermissions(
                mctx,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
}