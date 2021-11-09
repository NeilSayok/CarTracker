package com.example.neil.carlocator4l.PermissionHandler

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

class EasyPermissionsHasPermissions(val mctx: Context) {

     fun hasInternetPermission():Boolean {
        return EasyPermissions.hasPermissions(mctx, Manifest.permission.INTERNET)
    }
     fun hasVibratePermission():Boolean {
        return EasyPermissions.hasPermissions(mctx, Manifest.permission.VIBRATE)
    }
     fun hasCoarseLocationPermission():Boolean {
        return EasyPermissions.hasPermissions(mctx, Manifest.permission.ACCESS_COARSE_LOCATION)
    }
     fun hasFineLocationPermission():Boolean {
        return EasyPermissions.hasPermissions(mctx, Manifest.permission.ACCESS_FINE_LOCATION)
    }
}