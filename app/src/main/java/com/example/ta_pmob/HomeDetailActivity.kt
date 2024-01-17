package com.example.ta_pmob

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ta_pmob.databinding.ActivityHomeDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeDetailBinding

    private val dataRef = FirebaseDatabase
        .getInstance("https://historicvista-1414-default-rtdb.firebaseio.com")
        .getReference("DataLocation")

    private val sliderRef = FirebaseDatabase
        .getInstance("https://historicvista-1414-default-rtdb.firebaseio.com")
        .getReference("Recommendations")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationIdToShow = intent.getIntExtra(DATA_ID, -1)
        val isRecommendationData = intent.getBooleanExtra(RECOMMENDATION_ID, false)

        if(isRecommendationData) {
            fetchDataFromRecommendations(locationIdToShow)
        } else {
            fetchDataFromDataLocation(locationIdToShow)
        }

        binding.apply {
            fabGoToMaps.setOnClickListener {
                val dataWisata = binding.tvDetailNamaWisata.text.toString()
                val dataKota = binding.tvDetailNamaKota.text.toString()
                val dataDescription = binding.tvDescription.text.toString()

                if (locationIdToShow != null) {
                    launchMapsActivity(locationIdToShow, dataWisata, dataKota, dataDescription, dataRating = 0.0f)
                } else {
                    Log.e("HomeDetailActivity", "Data is missing")
                }
            }
            fabGoToHome.setOnClickListener {
                val intent = Intent(this@HomeDetailActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }

    }

    fun fetchDataFromDataLocation(locationIdToShow: Int?) {
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (locationSnapshot in snapshot.children) {
                    val dataId = locationSnapshot.key
                    val imageId = locationSnapshot.child("imageId").getValue(Int::class.java)
                    val dataWisata = locationSnapshot.child("namaWisata").getValue(String::class.java)
                    val dataKota = locationSnapshot.child("namaKota").getValue(String::class.java)
                    val dataDescription = locationSnapshot.child("description").getValue(String::class.java)
                    val dataRating = locationSnapshot.child("rating").getValue(Float::class.java)
                    val photoUrl = locationSnapshot.child("photoUrl").getValue(String::class.java)

                    Log.d("HomeDetailActivity",
                        "dataId: $dataId, " +
                                "imageId: $imageId, " +
                                "dataWisata: $dataWisata, " +
                                "dataKota: $dataKota, " +
                                "dataDescription: $dataDescription, " +
                                "dataRating: $dataRating, " +
                                "LocationId: $locationIdToShow")

                    if (imageId != null && imageId == locationIdToShow) {
                        binding.apply {
                            tvDetailNamaWisata.text = dataWisata
                            tvDetailNamaKota.text = dataKota
                            tvDescription.text = dataDescription
                            ratingTxt.text = dataRating.toString()
                        }

                        Glide.with(this@HomeDetailActivity)
                            .load(photoUrl)
                            .centerCrop()
                            .fitCenter()
                            .into(binding.ivMapsPhotos)

                        launchMapsActivity(
                            imageId,
                            dataWisata.toString(),
                            dataKota.toString(),
                            dataDescription.toString(),
                            dataRating,
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeDetailActivity", "Error fetching data: ${error.message}")
            }
        })
    }

    fun fetchDataFromRecommendations(locationIdToShow: Int?) {
        sliderRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (locationSnapshot in snapshot.children) {
                    val imageId = locationSnapshot.child("imageId").getValue(Int::class.java)
                    val dataWisata = locationSnapshot.child("namaWisata").getValue(String::class.java)
                    val dataKota = locationSnapshot.child("namaKota").getValue(String::class.java)
                    val dataDescription = locationSnapshot.child("description").getValue(String::class.java)
                    val dataRating = locationSnapshot.child("rating").getValue(Float::class.java)
                    val photoUrl = locationSnapshot.child("photoUrl").getValue(String::class.java)

                    if (imageId != null && imageId == locationIdToShow) {
                        binding.apply {
                            tvDetailNamaWisata.text = dataWisata
                            tvDetailNamaKota.text = dataKota
                            tvDescription.text = dataDescription
                            ratingTxt.text = dataRating.toString()
                        }

                        Glide.with(this@HomeDetailActivity)
                            .load(photoUrl)
                            .centerCrop()
                            .fitCenter()
                            .into(binding.ivMapsPhotos)

                        launchMapsActivity(
                            imageId,
                            dataWisata.toString(),
                            dataKota.toString(),
                            dataDescription.toString(),
                            dataRating,
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeDetailActivity", "Error fetching data: ${error.message}")
            }
        })
    }

    private fun launchMapsActivity(
        locationIdToShow: Int?,
        dataWisata: String,
        dataKota: String,
        dataDescription: String,
        dataRating: Float?
    ) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("LOCATION_ID", locationIdToShow)
        intent.putExtra(DATA_WISATA, dataWisata)
        intent.putExtra(DATA_KOTA, dataKota)
        intent.putExtra(DATA_DESCRIPTION, dataDescription)
        intent.putExtra(DATA_RATING, dataRating?: 0.0f)
        startActivity(intent)
    }

    companion object {
        const val DATA_ID = "data_id"
        const val DATA_WISATA = "data_wisata"
        const val DATA_KOTA = "data_kota"
        const val DATA_DESCRIPTION = "data_description"
        const val DATA_RATING = "data_rating"
        const val DATA_PHOTOS = "data_photos"
        const val RECOMMENDATION_ID = "recommendation_id"
    }

}