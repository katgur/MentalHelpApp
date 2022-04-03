package com.example.mainscreenlayout.ui.splash

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.Record
import com.example.mainscreenlayout.model.PersonalDatabase
import com.example.mainscreenlayout.ui.splash.SplashFragment
import java.util.LinkedHashMap

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("theme", false)
        if (mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        loadSplashFragment()
    }

    private fun loadSplashFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, SplashFragment.newInstance())
            .disallowAddToBackStack()
            .commit()
    }
}