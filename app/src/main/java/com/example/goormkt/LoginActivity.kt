package com.example.goormkt

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.goormkt.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

    private val authStateListener = FirebaseAuth.AuthStateListener() {
        val user : FirebaseUser? = firebaseAuth.currentUser
        if (user != null) {
            //로그인
            val i = Intent(this,ChatmainActivity::class.java)
            startActivity(i)
            finish()
        }else {
            //로그아웃
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth.signOut()

        val splash_background : String = remoteConfig.getString("splash_background")
        window.statusBarColor = Color.parseColor(splash_background)

        binding.loginLoginBtn.setBackgroundColor(Color.parseColor(splash_background))
        binding.loginSignupBtn.setBackgroundColor(Color.parseColor(splash_background))

        binding.loginLoginBtn.setOnClickListener {
            loginEvent()
        }

        binding.loginSignupBtn.setOnClickListener {
            val i = Intent(this,SignupActivity::class.java)
            startActivity(i)
        }

//        val authStateListener = FirebaseAuth.AuthStateListener() {
//            val user : FirebaseUser? = firebaseAuth.currentUser
//            if (user != null) {
//                //로그인
//                val i = Intent(this,ChatmainActivity::class.java)
//                startActivity(i)
//                finish()
//            }else {
//                //로그아웃
//            }
//        }
    }
    private fun loginEvent() {
        val id = binding.loginIdEt.text.toString()
        val pw = binding.loginPasswordEt.text.toString()
        firebaseAuth.signInWithEmailAndPassword(id,pw).addOnCompleteListener(this) {task ->
            if (!task.isSuccessful) {
                //로그인을 실패했을 때
                Toast.makeText(this,task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}