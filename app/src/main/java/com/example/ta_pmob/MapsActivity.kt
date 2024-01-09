package com.example.ta_pmob

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

    // Indeks saat ini yang menunjukkan koordinat mana yang akan ditampilkan
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan ID lokasi dari Intent (jika ada)
        locationIdToShow = intent.getIntExtra("LOCATION_ID", -1)
        Log.d("MapsActivity", "Received Location ID: $locationIdToShow")

        // Isi daftar koordinat dengan beberapa lokasi
        listOfCoordinates.add(LatLng(-7.74122437674232, 110.49272674467083))  // Candi Prambanan\
        listOfCoordinates.add(LatLng(-7.749774756043545, 110.50734069302234)) //Candi Miri
        listOfCoordinates.add(LatLng(-7.798843777418583, 110.4660705103882)) //Candi Abang
        listOfCoordinates.add(LatLng(-7.7520, 110.4919))  // Taman Sari
        listOfCoordinates.add(LatLng(-7.7975, 110.3687))  // Candi Ratu Boko
        listOfCoordinates.add(LatLng(-7.7745, 110.4157))  // Malioboro Street
        listOfCoordinates.add(LatLng(-7.5616, 110.7266))  // Borobudur Temple

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (locationIdToShow != -1) {
            // Jika ID lokasi diterima, cari indeks koordinat yang sesuai
            val foundIndex = locationIdToShow - 1  // Adjust to 0-based index
            if (foundIndex in 0 until listOfCoordinates.size) {
                currentIndex = foundIndex
            }
        }

        // Tampilkan lokasi berdasarkan indeks
        showMarkerForCurrentIndex()

        // Setelah marker diklik, tampilkan pesan toast
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

            mMap.clear() // Hapus marker sebelum menambah yang baru
            mMap.addMarker(MarkerOptions().position(currentCoordinate).title(currentLocationName))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinate, 12f))
        }
    }

    private fun getLocationNameByIndex(index: Int): String {
        // Mengembalikan nama lokasi berdasarkan indeks
        return when (index) {
            0 -> "Candi Prambanan"
            1 -> "Candi Miri"
            2 -> "Candi Abang"
            3 -> "Taman Sari"
            4 -> "Candi Ratu Boko"
            5 -> "Malioboro Street"
            6 -> "Borobudur Temple"
            else -> "Unknown Location"
        }
    }

    // Methode ini dipanggil saat tombol "Next Location" diklik
    fun showNextLocation(view: View) {
        currentIndex = (currentIndex + 1) % listOfCoordinates.size
        showMarkerForCurrentIndex()
    }
}