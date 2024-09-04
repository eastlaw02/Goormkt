package com.example.goormkt

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.goormkt.databinding.ActivitySplashBinding
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.setDefaultsAsync(R.xml.default_config)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result

                } else {

                }
                displayMessage()
            }
    }
    private fun displayMessage(){
        val splash_background : String = remoteConfig.getString("splash_background")
        val caps : Boolean = remoteConfig.getBoolean("splash_message_caps")
        val splash_message : String = remoteConfig.getString("splash_message")

        binding.splashLl.setBackgroundColor(Color.parseColor(splash_background))

        if(caps) {
            val builder : AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage(splash_message).setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                finish()
            })

            builder.create().show()
        } else {
            val i = Intent(this,LoginActivity::class.java)
            startActivity(i)
        }
    }
}