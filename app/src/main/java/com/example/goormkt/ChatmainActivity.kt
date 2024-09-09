package com.example.goormkt

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.goormkt.databinding.ActivityChatmainBinding

class ChatmainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatmainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatmainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}