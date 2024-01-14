package com.example.ta_pmob.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.ta_pmob.HomeActivity
import com.example.ta_pmob.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Progress Bar
        progressBar = binding.progressBar

        binding.btnLoginHome.setOnClickListener {
            showProgressBar()
            val intent = Intent(this, HomeActivity::class.java)

            binding.root.postDelayed({
                startActivity(intent)
                hideProgressBar()
            }, 500)
        }

        binding.tvHaventAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }




        //        binding.btnLoginHome.setOnClickListener {
//            val email = binding.etEmail.text.toString()
//            val password = binding.etPassword.text.toString()
//
//            if (email.isNotEmpty() && password.isNotEmpty()) {
//                // Proses login menggunakan Firebase Authentication
//                auth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this) { task ->
//                        if (task.isSuccessful) {
//                            // Login berhasil
//                            startActivity(Intent(this, HomeActivity::class.java))
//                            finish()
//                        } else {
//                            // Login gagal
//                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//            } else {
//                // Menangani kasus di mana email atau password kosong
//                Toast.makeText(baseContext, "Email and password cannot be empty.", Toast.LENGTH_SHORT).show()
//            }
//        }

    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

}
