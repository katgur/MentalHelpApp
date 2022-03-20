package com.example.mainscreenlayout.model


import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.domain.Message
import com.example.mainscreenlayout.domain.Record
import com.google.firebase.firestore.DocumentSnapshot
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ExerciseRepository(private val id: String) {

    private lateinit var doc : DocumentSnapshot

    private val source = MediatorLiveData<String>()
    private val commands = MediatorLiveData<List<String>>()
    private lateinit var record : Record
    private lateinit var context : Context

    val steps = source.map {
        Message(it, "bot", 0)
    }
    private var step = 0
    private var finished = false

    fun observeCommands(owner: LifecycleOwner, observer: Observer<List<String>>) {
        commands.observe(owner, observer)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onLoad() {
        //todo convertion move to another method or class
        val columns = LinkedHashMap<String, String>()
        for (column in (doc["columns"] as List<String>)) {
            columns[column] = ""
        }
        record = Record(0, id, columns)
        refreshCommands()
        val intro = doc["intro"] as String
        source.value = intro
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onFinished() {
        commands.value = emptyList()
        val recordId = PersonalDatabase.getInstance(context).dao().addRecord(record)
        val historyItem = HistoryItem(0, recordId,-1,"Пройдено упражнение " + (doc["name"].toString()), LocalDateTime.now().format(
            DateTimeFormatter.BASIC_ISO_DATE))
        PersonalDatabase.getInstance(context).dao().addHistoryItem(historyItem)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun load(context : Context) {
        if (id != "mock") {
            val promise = FirestoreDatabase.alternativeGet("exercises/$id")
            promise.addOnSuccessListener {
                doc = it
                onLoad()
            }.addOnFailureListener {
                Log.e("ExerciseRepository::load", "Failed to load exercise")
            }
            this.context = context
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshCommands() {
        val command = (doc["commands"] as Map<String, String>)[step.toString()]
        if (command != null) {
            commands.value = listOf("Эффективность", "Время", "Далее", command)
        }
        else {
            commands.value = listOf("Эффективность", "Время", "Далее")
        }
     }

    fun addEfficiencyStep() {
        val efficiency = doc["efficiency"] as String
        source.value = efficiency
    }

    fun addDurationStep() {
        val duration = doc["duration"] as String
        source.value = duration
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNextStep() {
        val steps = doc["steps"] as ArrayList<String>
        if (step == steps.size) {
            onFinished()
            return
        }
        source.value = steps[step]
        step++
    }

    fun addToRecord(content : String) {
        record.add(content)
    }
}