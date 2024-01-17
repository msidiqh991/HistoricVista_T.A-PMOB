package com.example.ta_pmob

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_pmob.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private val databaseReference: DatabaseReference = FirebaseDatabase
        .getInstance("https://historicvista-1414-default-rtdb.firebaseio.com")
        .getReference("DataUser")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tampilData()

        binding.accountSettingsButton.setOnClickListener {
            val intent = Intent(this, AccountSettingsActivity::class.java)
            startActivity(intent)
        }
        binding.backToHomepage.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }



    private fun tampilData() {
        // Menambahkan ValueEventListener ke referensi database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data in dataSnapshot.children) {
                    // Mendapatkan nilai dari setiap field data
                    val nama = data.child("nama").value.toString()

                    binding.apply {
                        textView.text = nama
                    }

                    Log.d("DataUser", "Nama: $nama")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("DataUser", "Failed to read value.", databaseError.toException())
            }
        })

        auth = FirebaseAuth.getInstance()

        // Ambil informasi pengguna yang sudah login
        val user = auth.currentUser
        if (user != null) {
            val userEmail = user.email

            binding.apply {
                textView2.text = userEmail
            }

        } else {
            Toast.makeText(this@ProfileActivity, "Ada kesalahan dalam melakukan Login", Toast.LENGTH_SHORT).show()
        }
    }

}