package com.example.mainscreenlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mainscreenlayout.ui.splash.SplashFragment

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        loadSplashFragment()
    }

    private fun loadSplashFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, SplashFragment.newInstance())
            .disallowAddToBackStack()
            .commit()
    }
}