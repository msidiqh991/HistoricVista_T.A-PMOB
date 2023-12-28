package com.example.ta_pmob

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_pmob.databinding.ActivityHomeDetailBinding

class HomeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_detail)
        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        TODO: ?
        binding.fabGoToMaps.setOnClickListener {
            // Mengambil ID atau indeks lokasi yang ingin ditampilkan (misalnya, indeks ke-2)
            val locationIdToShow = 2

            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("LOCATION_ID", locationIdToShow)
            startActivity(intent)
        }
    }
}
