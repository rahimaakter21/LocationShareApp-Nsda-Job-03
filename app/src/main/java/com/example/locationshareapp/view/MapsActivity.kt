package com.example.locationshareapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.locationshareapp.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.locationshareapp.databinding.ActivityMapsBinding
import com.example.locationshareapp.viewModel.FirestoreViewModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private  lateinit var  firestoreViewModel: FirestoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMapsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModel::class.java)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnZoomIn.setOnClickListener {
          mMap .animateCamera(CameraUpdateFactory.zoomIn())
        }
        binding.btnZoomOut.setOnClickListener {
            mMap .animateCamera(CameraUpdateFactory.zoomOut())
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        firestoreViewModel.getAllUsers(this){

          for (user in it) {

        val userLocation = user.location

        if (userLocation.isEmpty()|| userLocation == "Don't found any location yet"|| userLocation == "Location not available") {
          LatLng( 23.777176,90.399452)
        }
        else{
            val latLng = parseLocation(userLocation)
                val markerOptions = MarkerOptions().position(latLng).title(user.displayName)
                googleMap.addMarker(markerOptions)
        }
              val latLng = parseLocation(userLocation)
              val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
              googleMap.moveCamera(cameraUpdate)
     }
    }
}
    private fun parseLocation(location: String): LatLng {
        val latLngSplit = location.split(", ")
        val latitude = latLngSplit[0].substringAfter("Lat: ").toDouble()
        val longitude = latLngSplit[1].substringAfter("Long: ").toDouble()
        return LatLng(latitude, longitude)
    }
}