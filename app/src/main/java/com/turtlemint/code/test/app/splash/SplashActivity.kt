package com.turtlemint.code.test.app.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.turtlemint.code.test.app.databinding.LayoutActivitySplashBinding
import com.turtlemint.code.test.app.home.activities.HomeActivity
import java.util.*

class SplashActivity :AppCompatActivity() {

    private lateinit var binding : LayoutActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val runSplash : Timer = Timer()
        val showSplash = object : TimerTask(){
            override fun run() {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        runSplash.schedule(showSplash,3000)

    }
}