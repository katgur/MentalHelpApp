package com.example.mainscreenlayout.model


import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.DocumentSnapshot

class ExerciseRepository(private val id: String) {

    private lateinit var doc : DocumentSnapshot

    private val source = MediatorLiveData<String>()
    private val commands = MediatorLiveData<List<String>>()
    private var record : Record? = null

    val steps = source.map {
        Message(it, "bot", 0)
    }
    private var step = 0
    private var finished = false

    fun observeCommands(owner: LifecycleOwner, observer: Observer<List<String>>) {
        commands.observe(owner, observer)
    }

    fun load() {
        if (id != "mock") {
            val promise = FirestoreDatabase.alternativeGet("exercises/$id")
            promise.addOnSuccessListener {
                doc = it
                refreshCommands()
                addIntroStep()
            }.addOnFailureListener {
                Log.e("ExerciseRepository::load", "Failed to load exercise")
            }
        }
    }

    fun refreshCommands() {
        if (!finished) {
            val command = (doc["commands"] as Map<String, String>)[step.toString()]
            if (command != null) {
                commands.value = listOf("Эффективность", "Время", "Далее", command)
            }
            else {
                commands.value = listOf("Эффективность", "Время", "Далее")
            }
        } else {
            commands.value = emptyList()
        }
     }

    private fun addIntroStep() {
        val intro = doc["intro"] as String
        source.value = intro
    }

    fun addEfficiencyStep() {
        val efficiency = doc["efficiency"] as String
        source.value = efficiency
    }

    fun addDurationStep() {
        val duration = doc["duration"] as String
        source.value = duration
    }

    fun addNextStep() {
        val steps = doc["steps"] as ArrayList<String>
        if (step == steps.size) {
            finished = true
            return
        }
        source.value = steps[step]
        step++
    }

    fun addToRecord(content : String) {
        record?.add(content)
    }
}