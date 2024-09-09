package com.example.goormkt

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.google.firebase.storage.FirebaseStorage

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private lateinit var imageUri : Uri
    private val PICK_FROM_ALBUM = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupProfileImgIv.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK)
            i.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(i,PICK_FROM_ALBUM)
        }

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

                        val uid : String = task.result.user?.uid.toString()
                        FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(imageUri).addOnCompleteListener(this) {task2 ->
                            if (task2.isSuccessful) {
                                @SuppressWarnings("VisibleForTests")
                                val imageUrl : String = task2.result.storage.downloadUrl.toString()

                                val userModel = UserModel()
                                userModel.userName = binding.signupNameEt.text.toString()
                                userModel.profileImageUrl = imageUrl
                                
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel)
                            } else {
                                Log.d("signup", "Image upload failed: ${task2.exception?.message}")
                            }
                        }
                    } else {
                        //테스트 결과 password는 6자리 이상이어야 함!
                        Log.d("signup","fail: ${task.exception?.message}")
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK) {
            binding.signupProfileImgIv.setImageURI(data!!.data) // 표시되는 뷰 이미지 변경
            imageUri = data.data!! //이미지 경로 원본
        }
    }
}