package com.example.gameguide

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.gameguide.notification.NotificationRepo
import com.example.gameguide.settingUtil.SettingUtil


class SplashScreen :  AppCompatActivity(){

    private lateinit var sharedPreference: SharedPreferences
    private lateinit var setting: SettingUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        setting = SettingUtil(this)
        sharedPreference = this.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val localeToSet = sharedPreference.getString("LOCALE_TO_SET", "en")!!
        setting.setLocate(localeToSet)
        sharedPreference = getSharedPreferences("Settings", Context.MODE_PRIVATE)

        val darkMode = sharedPreference.getBoolean("DARK_MODE", false)

        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        NotificationRepo().myNotification(MainActivity())

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