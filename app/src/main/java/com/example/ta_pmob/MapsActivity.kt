package com.example.ta_pmob

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_pmob.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var locationIdToShow: Int = -1

    // Contoh daftar koordinat
    private val listOfCoordinates = mutableListOf<LatLng>()

    // Indeks yang menunjukkan koordinat yang akan ditampilkan
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan ID lokasi dari Intent (jika ada)
        locationIdToShow = intent.getIntExtra("LOCATION_ID", -1)
        Log.d("MapsActivity", "Received Location ID: $locationIdToShow")

        // Isi daftar koordinat dengan beberapa lokasi
        listOfCoordinates.add(LatLng(-7.74122437674232, 110.49272674467083))
        listOfCoordinates.add(LatLng(-7.8295649,110.3957762))
        listOfCoordinates.add(LatLng(-7.8006704, 110.3633676))
        listOfCoordinates.add(LatLng(-7.7520, 110.4919))
        listOfCoordinates.add(LatLng(-7.7975, 110.3687))
        listOfCoordinates.add(LatLng(-7.7745, 110.4157))
        listOfCoordinates.add(LatLng(-7.5616, 110.7266))

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        RelationToAnotherActivities()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (locationIdToShow != -1) {
            val foundIndex = locationIdToShow - 1  // Adjust to 0-based index
            if (foundIndex in 0 until listOfCoordinates.size) {
                currentIndex = foundIndex
            }
        }

        showMarkerForCurrentIndex()

        mMap.setOnMarkerClickListener { marker ->
            Toast.makeText(this@MapsActivity, "Clicked on ${marker.title}", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun showMarkerForCurrentIndex() {
        Log.d("MapsActivity", "Current Index: $currentIndex")
        if (currentIndex >= 0 && currentIndex < listOfCoordinates.size) {
            val currentCoordinate = listOfCoordinates[currentIndex]
            val currentLocationName = getLocationNameByIndex(currentIndex)

            mMap.clear()

            val markerOptions = MarkerOptions()
                .position(currentCoordinate)
                .title(currentLocationName)

            markerOptions.snippet("Unique and Iconic History place")
            mMap.addMarker(markerOptions)

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinate, 12f))
        }
    }

    private fun getLocationNameByIndex(index: Int): String {
        return when (index) {
            0 -> "Candi Prambanan"
            1 -> "Keraton Kotagede"
            2 -> "Benteng Vredeburg"
            3 -> "Taman Sari"
            4 -> "Candi Ratu Boko"
            5 -> "Malioboro Street"
            6 -> "Borobudur Temple"
            else -> "Unknown Location"
        }
    }

    private fun RelationToAnotherActivities() {
        binding.apply {
            val intent = Intent(this@MapsActivity, HomeDetailActivity::class.java)
            startActivity(intent)
        }
    }

    fun showNextLocation(view: View) {
        currentIndex = (currentIndex + 1) % listOfCoordinates.size
        showMarkerForCurrentIndex()
    }
}