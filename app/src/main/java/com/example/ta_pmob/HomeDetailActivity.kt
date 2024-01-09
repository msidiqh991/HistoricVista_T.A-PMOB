package com.example.ta_pmob

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_pmob.databinding.ActivityHomeDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val namaWisata = intent.getStringExtra(DATA_WISATA)
        val namaKota = intent.getStringExtra(DATA_KOTA)


        binding.apply {
            tvDetailNamaWisata.text = namaWisata
            tvDetailNamaKota.text = namaKota
        }

        val imageId = intent.getIntExtra(DATA_ID, -1)
        val locationIdToShow = imageId

        getDatafromDatabase(locationIdToShow)

        binding.fabGoToMaps.setOnClickListener {
            // Mengambil ID atau indeks lokasi yang ingin ditampilkan (misalnya, indeks ke-2)
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("LOCATION_ID", locationIdToShow)
            startActivity(intent)
        }
    }

    fun getDatafromDatabase(locationIdToShow: Int?) {
        val dataRef =  FirebaseDatabase.getInstance("https://pmob-pert9-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("DataLocation")

        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (locationSnapshot in snapshot.children) {
                    val dataId = locationSnapshot.child("data_id").getValue(Int::class.java)
                    val dataWisata = locationSnapshot.child("data_wisata").getValue(String::class.java)
                    val dataKota = locationSnapshot.child("data_kota").getValue(String::class.java)

                    if (dataId == locationIdToShow) {
                        binding.tvDetailNamaWisata.text = dataWisata
                        binding.tvDetailNamaKota.text = dataKota

                        launchMapsActivity(locationIdToShow, dataWisata.toString(), dataKota.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
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
