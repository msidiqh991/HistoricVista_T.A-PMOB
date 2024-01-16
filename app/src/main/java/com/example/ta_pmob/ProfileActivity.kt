package com.example.ta_pmob

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_pmob.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        InteractionButton()
    }

    private fun InteractionButton() {
        binding.apply {
            // Redirect to Homepage
            backToHomepage.setOnClickListener {
                val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
                startActivity(intent)
            }
            // Redirect to Account Settings
            accountSettingsButton.setOnClickListener {
                val intent = Intent(this@ProfileActivity, AccountSettingsActivity::class.java)
                startActivity(intent)
            }

        }
    }

}