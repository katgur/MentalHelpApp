package com.example.mainscreenlayout.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.domain.notification.EveryDayNotificationManager
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

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPref.registerOnSharedPreferenceChangeListener(this)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                Log.d("permission asking", "got result in requestPermissionLauncher")
                if (isGranted) {
                    Log.d("permission asking", "granted in Launcher")

                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    Log.d("permission asking", "not granted in Launcher")

                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val dataManager = PersonalDataManager(requireActivity())

            val deleteDataPrefBtn = findPreference<Preference>("delete_data")
            deleteDataPrefBtn?.setOnPreferenceClickListener {
                AlertDialog.Builder(activity)
                    .setMessage("Вы уверены, что хотите удалить все данные приложения?")
                    .setPositiveButton("Да") { _, _ ->
                        dataManager.deleteData()
                    }
                    .setNegativeButton("Нет") { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
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
            val password = sharedPreferences.getString("password", "")
            if (password == "") {
                return
            }
            getEncryptedSharedPreferences(this).edit()
                .putString("new_password", password)
                .apply()
            with (sharedPreferences.edit()) {
                remove("password")
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