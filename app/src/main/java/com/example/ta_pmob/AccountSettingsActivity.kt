package com.example.ta_pmob

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_pmob.databinding.ActivityAccountSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AccountSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountSettingsBinding
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle the update button click
        binding.apply {
            buttonUpdate.setOnClickListener {
                updateAccountSettings()
            }
        }
    }

    private fun updateAccountSettings() {
        val newEmail = binding.editTextEmail.text.toString()
        val newPassword = binding.editTextPassword.text.toString()
        val newName = binding.editTextFullname.text.toString()

        // Implement the logic to update email and password
        // You can use Firebase, Retrofit, or any other method to communicate with a backend server
        // and update the user's account settings.

        // For demonstration purposes, let's just print the values for now.
        println("New Email: $newEmail")
        println("New Password: $newPassword")


        user?.updateEmail(newEmail)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Email berhasil diperbarui
                } else {
                    // Terjadi kesalahan, tampilkan pesan kesalahan
                    Toast.makeText(this, "Gagal memperbarui email", Toast.LENGTH_SHORT).show()
                }
            }

        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Password berhasil diperbarui
                } else {
                    // Terjadi kesalahan, tampilkan pesan kesalahan
                    Toast.makeText(this, "Gagal memperbarui password", Toast.LENGTH_SHORT).show()
                }
            }

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val databaseReference = FirebaseDatabase.getInstance().getReference("DataUser")

        val newData = mapOf(
            "nama" to newName
        )

        userId?.let {
            databaseReference.child(it).updateChildren(newData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Data berhasil diperbarui di Realtime Database
                    } else {
                        // Terjadi kesalahan, tampilkan pesan kesalahan
                        Toast.makeText(this, "Gagal memperbarui data nama", Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }
}
