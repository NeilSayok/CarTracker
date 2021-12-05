package com.example.neil.carlocatoruserside1m.Fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.neil.carlocatoruserside1m.R
import com.example.neil.carlocatoruserside1m.ViewModels.SingleUSerDataVMFactory
import com.example.neil.carlocatoruserside1m.ViewModels.SingleUserDataViewModel
import com.example.neil.carlocatoruserside1m.ViewModels.UserDataViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(),OnMapReadyCallback {

    private lateinit var v: View

    var uid : Int = -1

    private lateinit var userData: UserData
    private lateinit var viewModel : SingleUserDataViewModel
    private lateinit var mMap : GoogleMap
    private var m: Marker? = null

    private lateinit var markerOptions: MarkerOptions


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        uid = requireArguments().getInt("id",0)
        v = inflater.inflate(R.layout.activity_maps, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this,SingleUSerDataVMFactory(requireActivity().application,uid))[SingleUserDataViewModel::class.java]
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?


        viewModel.userData.observe(viewLifecycleOwner, Observer {
            mapFragment?.getMapAsync(this)


        })



    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val startLocation = LatLng(viewModel.userData.value!!.latitude!!, viewModel.userData.value!!.longitude!!)
        markerOptions = MarkerOptions().position(startLocation).title("Here is your car")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name))
        m = mMap.addMarker(markerOptions)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(startLocation))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 16f))
    }
}