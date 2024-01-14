package com.example.ta_pmob

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ta_pmob.adapters.AdapterMaps
import com.example.ta_pmob.adapters.ImageAdapter
import com.example.ta_pmob.databinding.ActivityHomeBinding
import com.example.ta_pmob.models.ImageItem
import com.example.ta_pmob.models.MapsImageModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPager2 : ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    private val dataRef = FirebaseDatabase
        .getInstance("https://historicvista-1414-default-rtdb.firebaseio.com")
        .getReference("DataLocation")

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView Maps Nearby location section
        val recyclerView: RecyclerView = binding.rvLocationList
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)

//        binding.rvLocationList.layoutManager = LinearLayoutManager(this)
//        binding.rvLocationList.setHasFixedSize(true)

        // Navigate to Another Activity
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navbar_bottom)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_navBottom -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.detailMaps_navBottom -> {
                    val intent = Intent(this, HomeDetailActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile_navBottom -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        showDataMaps()
        showImageSlider()
    }

    private fun showDataMaps() {
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val locationList = mutableListOf<MapsImageModel>()
                val adapter = AdapterMaps(locationList)

                for(dataSnapshot in snapshot.children) {
                    val locationKey = dataSnapshot.getValue(MapsImageModel::class.java)
                    locationKey?.let {
                        locationList.add(it)
                        Log.d("Cek Data Firebase", it.toString())
                    }
                }

                if(locationList.isNotEmpty()) {
                    binding.rvLocationList.adapter = adapter

                    adapter.setOnItemClickListener(object : AdapterMaps.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val locationIdToShow = locationList[position].imageId
                            navigateToHomeDetail(locationIdToShow)
                        }
                    })
                } else {
                    Toast.makeText(this@HomeActivity, "Data tidak tersedia", Toast.LENGTH_LONG).show()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Ada kesalahan pada database", "${error.message}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager2.unregisterOnPageChangeCallback(pageChangeListener)
    }

    private fun navigateToHomeDetail(locationIdToShow: Int?) {
        val intent = Intent(this, HomeDetailActivity::class.java)
        intent.putExtra(HomeDetailActivity.DATA_ID, locationIdToShow)
        startActivity(intent)
    }

    private fun showImageSlider() {
        viewPager2 = findViewById<ViewPager2>(R.id.viewpager2)

        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val imageList = mutableListOf<ImageItem>()

                for (dataSnapshot in snapshot.children) {
                    val imageId = dataSnapshot.child("imageId").getValue(Int::class.java)
                    val photoUrl = dataSnapshot.child("photoUrl").getValue(String::class.java)
                    val namaWisata = dataSnapshot.child("namaWisata").getValue(String::class.java)
                    val namaKota = dataSnapshot.child("namaKota").getValue(String::class.java)

                    val imageItem = imageId?.let {
                        ImageItem(
                            it,
                            photoUrl.toString(),
                            namaWisata.toString(),
                            namaKota.toString(),
                        )
                    }

                    if (imageItem != null) {
                        imageList.add(imageItem)
                    }
                }

                val imageAdapter = ImageAdapter()
                viewPager2.adapter = imageAdapter
                imageAdapter.submitList(imageList)

                imageAdapter.setOnImageItemClickListener { imageItem ->
                    // Redirect ke HomeDetailActivity dengan membawa data dari item yang diklik
                    val intent = Intent(this@HomeActivity, HomeDetailActivity::class.java)
                    val locationIdToShow = imageItem.imageId
                    intent.putExtra(HomeDetailActivity.DATA_ID, locationIdToShow)
                    startActivity(intent)
                }

                val slideDotLL = findViewById<LinearLayout>(R.id.slideDotLL)
                val dotsImage = Array(imageList.size) { ImageView(this@HomeActivity) }

                dotsImage.forEach {
                    it.setImageResource(R.drawable.non_active_dot)
                    slideDotLL.addView(it, params)
                }

                dotsImage[0].setImageResource(R.drawable.active_dot)

                pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        dotsImage.mapIndexed { index, imageView ->
                            if (position == index) {
                                imageView.setImageResource(R.drawable.active_dot)
                            } else {
                                imageView.setImageResource(R.drawable.non_active_dot)
                            }
                        }
                        super.onPageSelected(position)
                    }
                }
                viewPager2.registerOnPageChangeCallback(pageChangeListener)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled if needed
            }
        })

    }

}