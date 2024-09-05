package com.example.goormkt

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.goormkt.databinding.ActivitySignupBinding
import com.example.goormkt.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splash_background : String = remoteConfig.getString(getString(R.string.rc_color))
        window.statusBarColor = Color.parseColor(splash_background)
        binding.signupSignupBtn.setBackgroundColor(Color.parseColor(splash_background))

        binding.signupSignupBtn.setOnClickListener {

            if(binding.signupEmailEt.text.toString() == null || binding.signupNameEt.text.toString() == null || binding.signupPasswordEt.text.toString() == null) {
                return@setOnClickListener
            }
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(binding.signupEmailEt.text.toString(),binding.signupPasswordEt.text.toString())
                .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful) {
                        Log.d("signup","success")
                        val userModel = UserModel()
                        userModel.userName = binding.signupNameEt.text.toString()

                        val uid : String = task.result.user?.uid.toString()
                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel)
                    } else {
                        //테스트 결과 password는 6자리 이상이어야 함!
                        Log.d("signup","fail: ${task.exception?.message}")
                    }
                }
        }
    }
}