package com.example.ta_pmob

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_pmob.authentication.LoginActivity
import com.example.ta_pmob.authentication.RegisterActivity
import com.example.ta_pmob.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnLogin?.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            binding.btnRegister.setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
    }
}