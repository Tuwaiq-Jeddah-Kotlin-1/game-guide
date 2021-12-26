package com.example.gameguide

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreference = this.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val localeToSet = sharedPreference.getString("LOCALE_TO_SET", "en")!!
        if (localeToSet == "ar"){
            setLocate("ar")
        }
        else{
            setLocate("en")
        }


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController




        /*navController = findNavController(R.id.fragmentContainerView)*/

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        bottomNavigationView.setupWithNavController(navController)

        /*navController.popBackStack(R.id.homepage, false)*/


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

        /*setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return  navController.navigateUp() ||  super.onSupportNavigateUp()
    }*/

    }



    private fun setLocate(s: String) {


        val locale = Locale(s)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale

        //---------------------------------------------------------------
        this?.resources?.updateConfiguration(config, this.resources.displayMetrics)

    }
}






