package com.example.ta_pmob

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_pmob.databinding.ActivityAccountSettingsBinding

class AccountSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountSettingsBinding

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

        // Implement the logic to update email and password
        // You can use Firebase, Retrofit, or any other method to communicate with a backend server
        // and update the user's account settings.

        // For demonstration purposes, let's just print the values for now.
        println("New Email: $newEmail")
        println("New Password: $newPassword")

        // Add your update logic here
    }
}