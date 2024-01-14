package com.example.ta_pmob

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationIdToShow = intent.getIntExtra(DATA_ID, -1)

        getDatafromDatabase(locationIdToShow)

        binding.apply {
            fabGoToMaps.setOnClickListener {
                val dataWisata = binding.tvDetailNamaWisata.text.toString()
                val dataKota = binding.tvDetailNamaKota.text.toString()

                if (locationIdToShow != null && dataWisata.isNotEmpty() && dataKota.isNotEmpty()) {
                    launchMapsActivity(locationIdToShow, dataWisata, dataKota)
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

    fun getDatafromDatabase(locationIdToShow: Int?) {
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (locationSnapshot in snapshot.children) {
                    val dataId = locationSnapshot.key // Get the unique key (e.g., NlWCJwy_vF7Stk-f4Nl)
                    val imageId = locationSnapshot.child("imageId").getValue(Int::class.java)
                    val dataWisata = locationSnapshot.child("namaWisata").getValue(String::class.java)
                    val dataKota = locationSnapshot.child("namaKota").getValue(String::class.java)
                    val dataPhotos = locationSnapshot.child("photoUrl").getValue(String::class.java)

                    Log.d("HomeDetailActivity",
                        "dataId: $dataId, " +
                                "imageId: $imageId, " +
                                "dataWisata: $dataWisata, " +
                                "dataKota: $dataKota, " +
                                "dataPhotos: $dataPhotos, " +
                                "LocationId: $locationIdToShow")

                    if (imageId != null && imageId == locationIdToShow) {
                        binding.apply {
                            tvDetailNamaWisata.text = dataWisata
                            tvDetailNamaKota.text = dataKota
                            ivMapsPhotos.text = dataPhotos

                        }

                        launchMapsActivity(
                            imageId,
                            dataWisata.toString(),
                            dataKota.toString(),
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeDetailActivity", "Error fetching data: ${error.message}")
            }
        })
    }

    private fun launchMapsActivity(locationIdToShow: Int?, dataWisata: String, dataKota: String) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("LOCATION_ID", locationIdToShow)
        intent.putExtra("DATA_WISATA", dataWisata)
        intent.putExtra("DATA_KOTA", dataKota)
        startActivity(intent)
    }
    
    companion object {
        const val DATA_ID = "data_id"
        const val DATA_WISATA = "data_wisata"
        const val DATA_KOTA = "data_kota"
    }

}
