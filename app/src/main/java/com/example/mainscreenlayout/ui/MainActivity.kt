package com.example.mainscreenlayout.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mainscreenlayout.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBottomNavigationBar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings_item -> {
                goToSettingsActivity()
                true
            }
            R.id.about_item -> {
                showAboutDialog()
                true
            }
            R.id.logout_item -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setBottomNavigationBar() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_chat,
                R.id.navigation_favorites, R.id.navigation_history
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun goToSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setMessage("Приложение выполнено в рамках курсового проекта 3 курса студенткой Гуровой Екатериной, БПИ192.\n" +
            "Упражнения и прочая информация взяты из книги по когнитивно-поведенческой терапии Мэттью Маккея Как победить стресс и депрессию.\n" +
            "Графические ресурсы взяты с сайта flaticon.com.")
            .setPositiveButton("Ок") { _,_ -> }
            .create()
            .show()
    }

    private fun logout() {
        finish()
    }
}