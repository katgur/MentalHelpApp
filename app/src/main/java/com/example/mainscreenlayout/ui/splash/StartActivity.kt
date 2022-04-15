package com.example.mainscreenlayout.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.model.FirestoreDatabase
import java.io.File

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("theme", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start)

        FirestoreDatabase.auth(this)

        loadSplashFragment()
    }

    private fun loadSplashFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, SplashFragment.newInstance())
            .disallowAddToBackStack()
            .commit()
    }
}