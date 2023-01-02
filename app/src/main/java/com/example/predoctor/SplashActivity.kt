package com.example.predoctor

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.predoctor.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.appNameText.apply {
            alpha = 0f
            animate().setDuration(2000).alpha(1f)
        }
        binding.appNameTextDescription.apply {
            alpha = 0f
            animate().setDuration(2500).alpha(1f).withEndAction {
                val intent = Intent(context,MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
                finish()

            }
        }

    }
}