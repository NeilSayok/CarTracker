package com.example.neil.carlocatoruserside1m.Fragments

import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.neil.carlocatoruserside1m.R
import com.example.neil.carlocatoruserside1m.Room.UserData
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MapsFragment : Fragment(),OnMapReadyCallback {

    private lateinit var v: View

    var uid : Int = -1

    private lateinit var userData: UserData
    private lateinit var viewModel : SingleUserDataViewModel
    private var mMap : GoogleMap? = null
    private var m: Marker? = null

    private lateinit var markerOptions: MarkerOptions

    private lateinit var lastSeenDateTV: TextView
    private lateinit var lastSeenTimeTV: TextView
    private lateinit var cityTV: TextView
    private lateinit var postalCodeTV: TextView






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

        lastSeenDateTV = v.findViewById(R.id.lastSeenDateTV)
        lastSeenTimeTV = v.findViewById(R.id.lastSeenTimeTV)
        cityTV = v.findViewById(R.id.cityTV)
        postalCodeTV = v.findViewById(R.id.postalCodeTV)




        viewModel.userData.observe(viewLifecycleOwner, Observer {
            if (mMap==null)
                mapFragment?.getMapAsync(this)
            else{
                    updateMap(it)


            }


        })



    }

    val dt = SimpleDateFormat("dd/M/yyyy")
    val tm = SimpleDateFormat("hh:mm:ss")

    private fun updateMap(data: UserData){
        try{
            val currLoc = LatLng(data.latitude!!, data.longitude!!)
            m?.position = currLoc
            mMap?.moveCamera(CameraUpdateFactory.newLatLng(currLoc))
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(data.latitude!!, data.longitude!!, 1)

            cityTV.text = "City: ${addresses[0].locality}"

            postalCodeTV.text = "Postal Code: " + addresses[0].postalCode

            val now = Date(data.time!!)

            lastSeenDateTV.text = "Last Seen Date: " + dt.format(now)

            lastSeenTimeTV.text = "Last Seen Time: " + tm.format(now)
        }catch (e: Exception){
            e.printStackTrace()
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val startLocation = LatLng(viewModel.userData.value!!.latitude!!, viewModel.userData.value!!.longitude!!)
        markerOptions = MarkerOptions().position(startLocation).title("Here is your car")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name))
        m = mMap!!.addMarker(markerOptions)

        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(startLocation))
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 16f))
    }

    override fun onDestroy() {
        super.onDestroy()
        //TODO stop all pending workmanagers

    }
}