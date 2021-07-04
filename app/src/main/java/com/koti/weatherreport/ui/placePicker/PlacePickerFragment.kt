package com.koti.weatherreport.ui.placePicker

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.button.MaterialButton
import com.koti.weatherreport.R
import com.koti.weatherreport.db.LocationBookMark
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*


/**
 * @author koti
 * Picker to pick location
 */
@AndroidEntryPoint
class PlacePickerFragment : Fragment(R.layout.fragment_place_picker) {
    private val TAG = "PlacePickerFragment"

    val viewModel by viewModels<PlacePickerViewModel>()

    //var declaration
    private var mPlacesClient: PlacesClient? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null

    // location retrieved by the Fused Location Provider.
    private var mLastKnownLocation: Location? = null
    // defult location.
    private val mDefaultLocation = LatLng(15.8844361, 80.3085502)
    private val DEFAULT_ZOOM = 15
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    private var mLocationPermissionGranted = false

    lateinit var  locationProgressBar:LinearLayout
    lateinit var dataHolder:RelativeLayout
    lateinit var _tvTitle:TextView
    lateinit var _tvAddressFullDetails:TextView
    lateinit var _btnSaveAddress:MaterialButton

    var activePickedAddress:LocationBookMark?=null

    lateinit var googleMap: GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        // Enable the zoom controls for the map
        googleMap.uiSettings.isZoomControlsEnabled = true;
        // Prompt the user for permission.
        getLocationPermission()
        //update marker
          updateLocationUI()
        //enable listner for marker dragging
        enableDragToSelectPosition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initPlacesClient()
    }

    private fun initViews() {
        try{
            view?.let {
                locationProgressBar= it.findViewById(R.id.locationProgressBar)
                dataHolder= it.findViewById(R.id.dataHolder)
                _tvTitle= it.findViewById(R.id.title)
                _tvAddressFullDetails= it.findViewById(R.id.addressFullDetails)
                _btnSaveAddress=it.findViewById<MaterialButton>(R.id.saveAddress).apply {
                    setOnClickListener {
                        activePickedAddress?.apply {
                            if(_id==0L){
                                val newId=viewModel.saveLocation(this)
                                if(newId>0L){
                                    _id=newId
                                    _btnSaveAddress.visibility=View.GONE
                                    Toast.makeText(requireContext(),"Added bookmark",Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(requireContext(),"Fail to add bookmark",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                it.findViewById<ImageButton>(R.id.actionBack).setOnClickListener {
                    requireActivity().onBackPressed()
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun bindData(newData:LocationBookMark){
        with(newData){
            _tvTitle.text=newData.name
            _tvAddressFullDetails.text=newData.address
            dataHolder.visibility=View.VISIBLE
            _btnSaveAddress.visibility=View.VISIBLE
            locationProgressBar.visibility=View.GONE
            activePickedAddress=this
        }
    }


    private fun enableDragToSelectPosition() {
        if(mLocationPermissionGranted){
            googleMap.setOnCameraIdleListener {
                val point=googleMap.cameraPosition.target
                processLocationFromLocationPoints(point)
            }
        }else{
            getLocationPermission()
        }
        /*googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {
//onMarkerDragStart
            }

            override fun onMarkerDrag(marker: Marker) {
//onMarkerDrag
            }

            override fun onMarkerDragEnd(marker: Marker) {
                val latLng: LatLng = marker.getPosition()
                println("drag End====>")
                processLocationFromLocationPoints(latLng)
            }

        });*/
    }

    private fun processLocationFromLocationPoints(point:LatLng){
        try {
            dataHolder.visibility=View.GONE
            locationProgressBar.visibility=View.VISIBLE
            val geocode = Geocoder(context, Locale.getDefault())
            val addressList = geocode.getFromLocation(point.latitude, point.longitude, 1)
            if(!addressList.isNullOrEmpty()){
                val address = addressList[0]
                println("new location====>$address")
                val newBookMark=LocationBookMark(
                    lat = point.latitude,
                    lon = point.longitude,
                    name =  if(!address.locality.isNullOrEmpty()){
                        address.locality
                    }else if(!address.featureName.isNullOrEmpty()){
                        address.featureName
                    }else{
                        "Unknown Location"
                    },
                    address = address.getAddressLine(0)
                )
                bindData(newBookMark)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun updateLocationUI() {
        try {
            if (mLocationPermissionGranted) {
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings.isMyLocationButtonEnabled = true
                pickCurrentPlace()
            } else {
                googleMap.isMyLocationEnabled = false
                googleMap.uiSettings.isMyLocationButtonEnabled = false
                mLastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    //request permissions
    private fun getLocationPermission() {
        mLocationPermissionGranted = false
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    fun initPlacesClient() {
        val apiKey = getString(R.string.google_maps_key)
        Places.initialize(requireContext(), apiKey)
        mPlacesClient = Places.createClient(requireContext())
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            requireContext()
        )
    }

    //find current location and add marker
    private fun getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                val locationResult = mFusedLocationProviderClient!!.lastLocation
                locationResult.addOnCompleteListener(requireActivity(),
                    OnCompleteListener<Location> { task ->
                        if (task.isSuccessful) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.result
                            mLastKnownLocation?.let {
                                Log.d(TAG, "Latitude: " + it.latitude)
                                Log.d(TAG, "Longitude: " + it.longitude)
                                addLocationMarker(LatLng(
                                    it.latitude,
                                    it.longitude
                                ))
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.")
                            Log.e(TAG, "Exception: %s", task.exception)
                            addLocationMarker()
                        }
                    })
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message!!)
        }
    }

    private fun pickCurrentPlace() {
        if (mLocationPermissionGranted) {
            getDeviceLocation()
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.")
            // Add a default marker, because the user hasn't selected a place.
            getLocationPermission()
            //addLocationMarker()
            // Prompt the user for permission.
        }
    }

    private fun addLocationMarker(locationPoints: LatLng=mDefaultLocation) {
        /*googleMap.clear()
        googleMap.addMarker(
            MarkerOptions()
                .title(getString(R.string.default_info_title))
                .position(locationPoints)
                .draggable(true)
                .snippet(getString(R.string.default_info_snippet))
        )*/

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                locationPoints, DEFAULT_ZOOM.toFloat()
            )
        )
    }

    /**
     * Handles the result of the request for location permissions
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    mLocationPermissionGranted = true
                    updateLocationUI()
                    enableDragToSelectPosition()
                }
            }
        }
    }

}