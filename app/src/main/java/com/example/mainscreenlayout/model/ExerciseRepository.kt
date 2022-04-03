package com.example.mainscreenlayout.model


import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.mainscreenlayout.domain.*
import com.google.firebase.firestore.DocumentSnapshot
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import kotlin.properties.Delegates

class ExerciseRepository(private val id: String) {

    private lateinit var doc : DocumentSnapshot

    private val source = MediatorLiveData<String>()
    private val commands = MediatorLiveData<List<String>>()
    private lateinit var record : Record
    private lateinit var context : Context

    private val recommendation = Recommendation()

    val steps = source.map {
        Message(it, "bot", 0)
    }
    val enterMode = MutableLiveData<Boolean>()
    private var isWritable = false

    private var step = 0
    private var finished = false

    fun observeCommands(owner: LifecycleOwner, observer: Observer<List<String>>) {
        commands.observe(owner, observer)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onLoad() {
        //todo convertion move to another method or class
        val columns = LinkedHashMap<String, String>()
        val columns1 = doc["columns"]
        if (columns1 != null) {
            for (column in columns1 as List<String>) {
                columns[column] = ""
            }
            isWritable = true
        } else {
            isWritable = false
        }
        record = Record(id, columns)
        refreshCommands()
        val intro = doc["intro"] as String
        source.value = intro
        enterMode.postValue(false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onFinished() {
        enterMode.postValue(false)
        commands.value = emptyList()
        PersonalDatabase.getInstance(context).dao().addRecord(record)
        val historyItem = HistoryItem(record.id,
            null,
            "Пройдено упражнение " + (doc["name"].toString()),
            LocalDateTime.now().atZone(ZoneId.of("Africa/Addis_Ababa")).toEpochSecond())
        PersonalDatabase.getInstance(context).dao().addHistoryItem(historyItem)
        GamificationSystem.updatePoints(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun load(context : Context) {
        val promise = FirestoreDatabase.alternativeGet("exercises/$id")
        promise.addOnSuccessListener {
            doc = it
            onLoad()
        }.addOnFailureListener {
            Log.e("ExerciseRepository::load", "Failed to load exercise")
        }
        this.context = context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun refreshCommands() {
        val commands = (doc["commands"] as Map<String, ArrayList<String>>)[step.toString()]
        if (step == 0) {
            if (id == "default") {
                this.commands.value = commands
            }
            else {
                this.commands.value = listOf("Эффективность", "Время", "Далее")

            }
        }
        else if (commands == null) {
            this.commands.value = listOf("Далее")
        }
        else {
            commands.add("Далее")
            this.commands.value = commands
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
        enterMode.postValue(isWritable)
        record.next()
    }

    fun addToRecord(content : String) {
        record.add(content)
    }

    fun addRecommendation() {
        //todo recommendation is always empty
        val recommended = recommendation.getRecommended()
        if (recommended.isNotEmpty()) {
            val index = (step - 1) % recommended.size
            source.value = recommended[index].name!!
            step++
        } else {
            source.value = "Сегодня для тебя нет рекомендаций. Возможно, ты ещё не прошёл/ла ежедневный опрос."
        }
        enterMode.postValue(false)
    }

    fun addQuote() {
        val quotes = doc["quotes"] as ArrayList<String>
        val index = (step - 1) % quotes.size
        source.value = quotes[index]
        step++
        enterMode.postValue(false)
    }

    fun addHelp() {
        val help = doc["help"] as ArrayList<String>
        val index = (step - 1) % help.size
        source.value = help[index]
        enterMode.postValue(isWritable)
    }
}