package com.example.gameguide.settingUtil

import android.content.Context
import android.content.res.Configuration
import java.util.*

class SettingUtil(private val context: Context) {
     fun setLocate(s: String) {


        val locale = Locale(s)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale

        context.resources?.updateConfiguration(config, context.resources.displayMetrics)

    }
}