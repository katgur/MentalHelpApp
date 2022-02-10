package com.example.mainscreenlayout.model

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData

class FirestoreRepository(private val context: FragmentActivity?) {

    private val dao = FirestoreDatabase(context)

    fun getExercises(): LiveData<List<String>> {
        return dao.get("exercises")
    }
}