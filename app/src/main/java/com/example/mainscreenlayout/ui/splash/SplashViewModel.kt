package com.example.mainscreenlayout.ui.splash

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager

class SplashViewModel : ViewModel() {

    fun getName(activity : Activity) : String? {
        return PreferenceManager.getDefaultSharedPreferences(activity).getString("name", null)
    }

    fun hasPassword(activity : Activity) : Boolean {
        return PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("add_password", false)
    }
}