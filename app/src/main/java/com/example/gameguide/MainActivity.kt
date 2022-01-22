package com.example.gameguide

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.gameguide.notification.NotificationRepo
import com.example.gameguide.settingUtil.SettingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreference: SharedPreferences

    private lateinit var navController: NavController
    private lateinit var setting: SettingUtil

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setting = SettingUtil(this)
        sharedPreference = this.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val localeToSet = sharedPreference.getString("LOCALE_TO_SET", "en")!!
        setting.setLocate(localeToSet)
        sharedPreference = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        setContentView(R.layout.activity_main)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.forgetPassword -> {
                    bottomNavigationView.visibility = View.GONE
                }
                R.id.signIn -> {
                    bottomNavigationView.visibility = View.GONE
                }
                R.id.registration -> {
                    bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }

/*    private fun loadData() {
    }*/
}






