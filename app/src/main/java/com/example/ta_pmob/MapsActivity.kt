package com.example.ta_pmob

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_pmob.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.IOException
import java.util.Locale


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var locationIdToShow: Int = -1
    private var dataWisata: String? = null

    private val locationDataList = mutableListOf<LocationData>()
    private val RecommendationDataList = mutableListOf<RecommendationData>()

    private val DBLocation = FirebaseDatabase
        .getInstance("https://historicvista-1414-default-rtdb.firebaseio.com")
        .getReference("DataLocation")

    private val OtherCountry = FirebaseDatabase
        .getInstance("https://historicvista-1414-default-rtdb.firebaseio.com")
        .getReference("Recommendations")

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationIdToShow = intent.getIntExtra("LOCATION_ID", -1)
        dataWisata = intent.getStringExtra(HomeDetailActivity.DATA_WISATA)
        Log.d("MapsActivity", "Received Location ID: $locationIdToShow")

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getDataFromFirebase()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (locationIdToShow != -1) {
            val foundIndexLocation = locationDataList.indexOfFirst {
                it.imageId == locationIdToShow
            }
            val foundIndexRecommendation = RecommendationDataList.indexOfFirst {
                it.imageId == locationIdToShow
            }

            if (foundIndexLocation != -1) {
                currentIndex = foundIndexLocation
            } else if (foundIndexRecommendation != -1) {
                currentIndex = foundIndexRecommendation
            }
        }

        showMarkerForCurrentIndex()

        mMap.setOnMarkerClickListener { marker ->
            Toast.makeText(this@MapsActivity, "Clicked on ${marker.title}", Toast.LENGTH_SHORT).show()
            false
        }
    }


//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        if (locationIdToShow != -1) {
//            val foundIndex = locationIdToShow - 1
//            if (foundIndex in 0 until locationDataList.size) {
//                currentIndex = foundIndex
//            }
//        }
//
//        showMarkerForCurrentIndex()
//
//        mMap.setOnMarkerClickListener { marker ->
//            Toast.makeText(this@MapsActivity, "Clicked on ${marker.title}", Toast.LENGTH_SHORT).show()
//            false
//        }
//    }

    private fun getDataFromFirebase() {
        DBLocation.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (locationSnapshot in snapshot.children) {
                    val imageId = locationSnapshot.child("imageId").getValue(Int::class.java)
                    val dataWisata = locationSnapshot.child("namaWisata").getValue(String::class.java)
                    val latitude = locationSnapshot.child("latitude").getValue(Double::class.java)
                    val longitude = locationSnapshot.child("longitude").getValue(Double::class.java)

                    if (imageId != null && latitude != null && longitude != null && dataWisata != null) {
                        locationDataList.add(LocationData(imageId, latitude, longitude, dataWisata))
                    }
                }

                if (locationIdToShow != -1) {
                    val foundIndex = locationDataList.indexOfFirst {
                        it.imageId == locationIdToShow
                    }
                    if (foundIndex != -1) {
                        currentIndex = foundIndex
                    }
                }

                showMarkerForCurrentIndex()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MapsActivity", "Error fetching data: ${error.message}")
            }
        })

        OtherCountry.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (locationSnapshot in snapshot.children) {
                    val imageId = locationSnapshot.child("imageId").getValue(Int::class.java)
                    val dataWisata = locationSnapshot.child("namaWisata").getValue(String::class.java)
                    val latitude = locationSnapshot.child("latitude").getValue(Double::class.java)
                    val longitude = locationSnapshot.child("longitude").getValue(Double::class.java)

                    if (imageId != null && latitude != null && longitude != null && dataWisata != null) {
                        RecommendationDataList.add(RecommendationData(imageId, latitude, longitude, dataWisata))
                    }
                }

                if (locationIdToShow != -1) {
                    val foundIndex = RecommendationDataList.indexOfFirst {
                        it.imageId == locationIdToShow
                    }
                    if (foundIndex != -1) {
                        currentIndex = foundIndex
                    }
                }

                showMarkerForCurrentIndex()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MapsActivity", "Error fetching data: ${error.message}")
            }
        })
    }

    private fun getLocationNameByCoordinates(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                return address.locality ?: "Unknown Location"
            }
        } catch (e: IOException) {
            e.message
        }
        return "Unknown Location"
    }

    private fun showMarkerForCurrentIndex() {
        val dataList: List<LocationData> = if (locationDataList.isNotEmpty()) {
            locationDataList.map { it as LocationData }
        } else if (RecommendationDataList.isNotEmpty()) {
            RecommendationDataList.map { it as LocationData }
        } else {
            emptyList()
        }

        if (currentIndex >= 0 && currentIndex < dataList.size) {
            val currentLocationData = dataList[currentIndex]
            val currentCoordinate = LatLng(currentLocationData.latitude, currentLocationData.longitude)
            val currentLocationDistrict = getLocationNameByCoordinates(
                currentCoordinate.latitude,
                currentCoordinate.longitude
            )

            mMap.clear()

            val markerOptions = MarkerOptions()
                .position(currentCoordinate)
                .title(currentLocationData.dataWisata)
                .snippet(currentLocationDistrict)

            mMap.addMarker(markerOptions)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinate, 12f))
        }
    }

//    private fun showMarkerForCurrentIndex() {
//        if (currentIndex >= 0 && currentIndex < locationDataList.size) {
//            val currentLocationData = locationDataList[currentIndex]
//            val currentCoordinate = LatLng(currentLocationData.latitude, currentLocationData.longitude)
//            val currentLocationDistrict = getLocationNameByCoordinates(
//                currentCoordinate.latitude,
//                currentCoordinate.longitude
//            )
//
//            mMap.clear()
//
//            val markerOptions = MarkerOptions()
//                .position(currentCoordinate)
//                .title(currentLocationData.dataWisata)
//                .snippet(currentLocationDistrict)
//
//            mMap.addMarker(markerOptions)
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinate, 12f))
//        }
//    }

    data class LocationData(
        val imageId: Int,
        val latitude: Double,
        val longitude: Double,
        val dataWisata: String
    )

    data class RecommendationData(
        val imageId: Int,
        val latitude: Double,
        val longitude: Double,
        val dataWisata: String,
    )

}