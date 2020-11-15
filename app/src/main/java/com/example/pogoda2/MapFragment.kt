package com.example.pogoda2

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Point
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*
import java.util.*
import kotlin.properties.Delegates

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {
    private lateinit var mMap: GoogleMap
    var latttitude by Delegates.notNull<Double>()
    var longitttude by Delegates.notNull<Double>()

    val KOMSOMOLSK = LatLng(49.015, 33.645)
    val ZOOM_LEVEL = 13f
    private lateinit var viewModelMain: MainViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelMain = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)



    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationButtonClickListener(this)               // нажатие на кнопку my location
        mMap.setOnMyLocationClickListener(this)                     // нажатие на синюю точку

        mMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(latlng: LatLng) {

                mMap.clear();                                            // Очищает ранее затронутую позицию

                mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));// Перемещает камеру в новую позицию

                val location = LatLng(latlng.latitude, latlng.longitude)
                latttitude = latlng.latitude
                longitttude = latlng.longitude
                mMap.addMarker(
                    MarkerOptions().position(location).title("$latttitude + ,$longitttude")
                        .draggable(true)
                )


                viewModelMain.refreshDataFromRepository(latttitude, longitttude)

            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_map, container, false)
        return view

    }


    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(context, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(context, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }


}


