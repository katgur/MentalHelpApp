package com.example.mainscreenlayout.model

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect

class ExerciseRepository(val name: String) {

    private val source = MediatorLiveData<List<String>>()
    val steps = source.map {
        Message(it[0], "bot", 0)
    }

    suspend fun addEfficiencyStep() {
        FirestoreDatabase.get("exercises/$name/efficiency").asFlow()
            .collect {
                source.setValue(it)
            }
    }

    suspend fun addDurationStep() {
        FirestoreDatabase.get("exercises/$name/duration").asFlow()
            .collect {
                source.setValue(it)
            }
    }
}