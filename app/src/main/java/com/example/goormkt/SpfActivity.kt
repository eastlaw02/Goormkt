package com.example.goormkt

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.goormkt.databinding.ActivitySpfBinding

class SpfActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpfBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadData()

        binding.spfBackBtn.setOnClickListener {
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()

        saveData()
    }
    // private은 해당 클래스 내에서만 사용가능하도록
    private fun saveData() {
        val pref = getSharedPreferences("pref",0)
        val edit = pref.edit() // 수정모드
        edit.putString("name",binding.helloEt.text.toString())
        edit.apply()// 1번째 인자 - Key값, 2번째 인자 - 실제 담아둘 값 (value값)
    }

    private fun loadData() {
        val pref = getSharedPreferences("pref",0)
        binding.helloEt.setText(pref.getString("name","")) // 1번째 인자는 저장할 당시 키값, 2번째는 키 값에 데이터가 존재하지 않을때
    }
}