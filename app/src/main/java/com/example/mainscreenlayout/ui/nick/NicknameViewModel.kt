package com.example.mainscreenlayout.ui.nick

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences

class NicknameViewModel : ViewModel() {

    fun addNickname(activity : Activity, nickname: String) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        with (sharedPref.edit()) {
            putString("name", nickname)
            apply()
        }
    }

    fun validatePassword(activity: Activity, password : String) : Boolean {
        val actualPassword = EncryptedSharedPreferences.create("password", "master", activity,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM).getString("encryptedPassword", null)
            ?: return true
        return actualPassword == password
    }
}