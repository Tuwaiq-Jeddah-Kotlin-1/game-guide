package com.example.gameguide

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class SplashScreen :  AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val splash = findViewById<ImageView>(R.id.splashLogo)
        splash.alpha= 0f
        splash.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent (  this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim. fade_in,android. R. anim. fade_out)
            finish()
        }
    }
}