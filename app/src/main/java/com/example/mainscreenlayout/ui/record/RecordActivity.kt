package com.example.mainscreenlayout.ui.record

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.ui.record.RecordFragment
import com.example.mainscreenlayout.ui.splash.SplashFragment

class RecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val extras = intent.extras
        if (extras != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.record_fcv, RecordFragment.newInstance(extras))
                .disallowAddToBackStack()
                .commit()
        }
    }
}