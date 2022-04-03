package com.example.mainscreenlayout.ui.market

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.ui.splash.SplashFragment

class MarketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)

        supportFragmentManager.beginTransaction()
            .replace(R.id.market_fcv, MarketFragment.newInstance())
            .disallowAddToBackStack()
            .commit()
    }
}