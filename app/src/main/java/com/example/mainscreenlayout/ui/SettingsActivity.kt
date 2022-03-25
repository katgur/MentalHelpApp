package com.example.mainscreenlayout.ui

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.model.PersonalDatabase
import com.example.mainscreenlayout.notification.EveryDayNotificationManager
import com.example.mainscreenlayout.utils.PersonalDataManager

class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        val mode = sharedPref.getBoolean("isNight", false)
        if (mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val dataManager = PersonalDataManager(requireActivity())

            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it[Manifest.permission.WRITE_EXTERNAL_STORAGE] == true) {
                    dataManager.exportData()
                } else {
                    dataManager.explainPermission("Это разрешение необходимо для создания резервной копии данных приложения.")
                }
                if (it[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
                    dataManager.importData()
                } else {
                    dataManager.explainPermission("Это разрешение необходимо для восстановления данных приложения.")
                }
            }

            val deleteDataPrefBtn = findPreference<Preference>("delete_data")
            deleteDataPrefBtn?.setOnPreferenceClickListener {
                //todo add dialog
                PersonalDatabase.getInstance(requireContext()).clearAllTables()
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext())
                with (sharedPref.edit()) {
                    remove("name")
                    apply()
                }
                return@setOnPreferenceClickListener true
            }

            val exportDataPrefBtn = findPreference<Preference>("export_data")
            exportDataPrefBtn?.setOnPreferenceClickListener {
                dataManager.exportData()
                return@setOnPreferenceClickListener true
            }

            val importDataPrefBtn = findPreference<Preference>("import_data")
            importDataPrefBtn?.setOnPreferenceClickListener {
                dataManager.importData()
                return@setOnPreferenceClickListener true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "theme") {
            if (sharedPreferences.getBoolean(key, false)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        else if (key == "password") {
            val password = sharedPreferences.getString("password", null)
            if (password == "") {
                return
            }
            val encryptedSharedPreferences = EncryptedSharedPreferences.create("password", "master", this,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
            with (encryptedSharedPreferences.edit()) {
                putString("encryptedPassword", password)
                apply()
            }
            with (sharedPreferences.edit()) {
                putString("password", "")
                apply()
            }
        }
        else if (key == "notifications") {
            val notificationFactory = EveryDayNotificationManager()
            if (sharedPreferences.getBoolean(key, false)) {
                notificationFactory.startNotifications(this)
            } else {
                notificationFactory.stopNotifications(this)
            }
        }
    }
}