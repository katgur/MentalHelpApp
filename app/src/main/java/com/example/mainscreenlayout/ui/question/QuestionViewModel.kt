package com.example.mainscreenlayout.ui.question

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.example.mainscreenlayout.domain.Answer
import com.example.mainscreenlayout.domain.GamificationSystem
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.model.FirestoreRepository
import com.example.mainscreenlayout.domain.Question
import com.example.mainscreenlayout.model.PersonalDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class QuestionViewModel : ViewModel() {

    private val repository = FirestoreRepository()
    private val questions = MediatorLiveData<List<Question>>()
    private val answers = ArrayList<String>()
    private val levels = ArrayList<Int>()

    private val index = MutableLiveData<Int>()
    private val selected = index.map {
        questions.value?.get(it)
    }

    var isUpdated : String? = null

    init {
        answers.addAll(listOf("", "", "", ""))
        viewModelScope.launch {
            repository.getQuestions().asFlow()
                .collect {
                    questions.value = listOf(it[0], it[1], it[3], it[2])
                    index.postValue(0)
                }
        }
    }

    fun observeSelected(owner : LifecycleOwner, observer : Observer<Question?>) {
        selected.observe(owner, observer)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun hasAnswersToday(activity: Activity) : Boolean {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        val last = sharedPref.getLong("last", -1)
        if (last != -1L) {
            return last == LocalDate.now().toEpochDay()
        }
        return false
    }

    fun nextQuestion() : Boolean {
        val current = index.value
        if (current == answers.size - 1 || current == null) {
            return false
        }
        index.value = current + 1
        return true
    }

    fun addAnswer(content : String, level : Int) {
        answers[index.value!!] = content
        levels.add(level)
    }

    fun saveAnswers(activity: Activity) {
        if (isUpdated == null) {
            val answer = Answer(answers.subList(0, 3), levels[1], levels[0], levels[2], answers[3])
            PersonalDatabase.getInstance(activity).dao().addAnswer(answer)

            val historyItem = HistoryItem(null, answer.id, "Вы прошли опросник эмоционального состояния",
                LocalDateTime.now().atZone(ZoneId.of("Africa/Addis_Ababa")).toEpochSecond())
            PersonalDatabase.getInstance(activity).dao().addHistoryItem(historyItem)

            val now = LocalDate.now().toEpochDay()
            val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
            with (sharedPref.edit()) {
                putLong("last", now)
                apply()
            }

            GamificationSystem.updatePoints(activity)
        } else {
            val answer = isUpdated?.let { Answer(answers.subList(0, 3), levels[0], levels[1], levels[2], answers[3], it) }
            if (answer != null) {
                PersonalDatabase.getInstance(activity).dao().updateAnswer(answer)
            }
        }
    }
}