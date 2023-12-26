//package com.example.ta_pmob
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//
//class AccountSettingsActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_account_settings)
//
//        // Handle the update button click
//        buttonUpdate.setOnClickListener {
//            updateAccountSettings()
//        }
//    }
//
//    private fun updateAccountSettings() {
//        val newEmail = editTextEmail.text.toString()
//        val newPassword = editTextPassword.text.toString()
//
//        // Implement the logic to update email and password
//        // You can use Firebase, Retrofit, or any other method to communicate with a backend server
//        // and update the user's account settings.
//
//        // For demonstration purposes, let's just print the values for now.
//        println("New Email: $newEmail")
//        println("New Password: $newPassword")
//
//        // Add your update logic here
//    }
//}