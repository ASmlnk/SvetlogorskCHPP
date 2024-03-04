package com.example.visionzero

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.text_recycle)

        val navHostFragment= supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
    override fun attachBaseContext(newBase: Context?) {

        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        val displayM = newBase?.resources?.displayMetrics
        if (displayM?.densityDpi != DisplayMetrics.DENSITY_DEVICE_STABLE)
        {
            newOverride.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE;
        }

        applyOverrideConfiguration(newOverride)
        super.attachBaseContext(newBase)
    }
}