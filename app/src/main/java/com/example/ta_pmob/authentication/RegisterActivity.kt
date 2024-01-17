package com.example.ta_pmob.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_pmob.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    private val databaseReference: DatabaseReference = FirebaseDatabase
        .getInstance("https://historicvista-1414-default-rtdb.firebaseio.com")
        .getReference("DataUser")

    private lateinit var etNama: EditText
    private lateinit var etEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etNama = binding.etFullname
        etEmail = binding.etEmail

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            tambahData()
        }

        binding.tvHaveAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun tambahData() {
        val nama = etNama.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (nama.isNotEmpty()) {
            // Membuat objek data baru
            val userData = UserData(nama)

            // Menambahkan data ke Firebase Realtime Database
            val dataKey = databaseReference.push().key
            if (dataKey != null) {
                databaseReference.child(dataKey).setValue(userData)
                etNama.text.clear()
            }
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Registration successful.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext,
                        "Registration failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

    data class UserData(val nama: String)

}

