package com.example.monopolydm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.monopolydm.activities.LoginActivity


class SplashScreen : AppCompatActivity() {

    val SPLASH_DURATION = 2000L
    val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val backgound = object : Thread() {
            override fun run() {
                try {
                    sleep(SPLASH_DURATION)
                    val intent = Intent(this@SplashScreen, LoginActivity::class.java)
                    startActivity(intent)

                }catch (e:Exception){
                    Log.d(TAG,e.message!!)
                }
            }
        }
        backgound.start()
    }
}
