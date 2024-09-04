package com.example.goormkt

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.goormkt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) { //앱이 처음 실행될 때 한번 수행하는 곳 (초기화)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainFirstStudy.setOnClickListener {
            val i = Intent(this,SpfActivity::class.java)
            startActivity(i)
        }

        binding.mainSecondStudy.setOnClickListener {
            val i = Intent(this,WebviewActivity::class.java)
            startActivity(i)
        }

        binding.mainThirdStudy.setOnClickListener {
            val i = Intent(this,CameraActivity::class.java)
            startActivity(i)
        }

        binding.mainChattingApp.setOnClickListener {
            val i = Intent(this,SplashActivity::class.java)
            startActivity(i)
        }
    }

}