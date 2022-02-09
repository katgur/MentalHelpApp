package com.example.mainscreenlayout

import android.app.Application
import com.example.mainscreenlayout.model.FirestoreDatabase

class AppDelegate : Application() {

    val firestoreDatabase = FirestoreDatabase()
}