package com.example.goormkt

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.goormkt.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val splash_background : String = remoteConfig.getString("splash_background")
        window.statusBarColor = Color.parseColor(splash_background)

        binding.loginLoginBtn.setBackgroundColor(Color.parseColor(splash_background))
        binding.loginSigninBtn.setBackgroundColor(Color.parseColor(splash_background))

    }
}