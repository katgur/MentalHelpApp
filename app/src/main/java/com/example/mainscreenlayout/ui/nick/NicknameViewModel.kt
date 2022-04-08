package com.example.mainscreenlayout.ui.nick

import android.app.Activity
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class NicknameViewModel : ViewModel() {

    fun addNickname(activity : Activity, nickname: String) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        with (sharedPref.edit()) {
            putString("name", nickname)
            apply()
        }
    }

    fun validatePassword(activity: Activity, password : String) : Boolean {
        val sharedPref = getEncryptedSharedPreferences(activity)
        val actual = sharedPref.getString("new_password", "")
        return actual == "" || actual == password
    }

    private fun getEncryptedSharedPreferences(activity: Activity): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            "secret_shared_prefs_file",
            masterKeyAlias,
            activity,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}