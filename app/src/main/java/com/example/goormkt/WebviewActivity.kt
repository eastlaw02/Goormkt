package com.example.goormkt

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.goormkt.databinding.ActivityWebviewBinding

class WebviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webView = binding.webView

        webView.settings.javaScriptEnabled = true // 자바 스크립트 허용

        // 웹뷰에서 새창이 뜨지 않도록 방지하는 구문
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        webView.loadUrl("https://www.naver.com") // 링크 주소를 Load 함
    }


    override fun onBackPressed() {
        if(binding.webView.canGoBack()) { //웹사이트에서 뒤로 갈 페이지가 존재할 경우
            binding.webView.goBack() //웹사이트 상에서 뒤로가기 기능
        } else {
            super.onBackPressed() //본래 안드로이드 상 뒤로가기 버튼 기능
        }

    }
}