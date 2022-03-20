package com.example.mainscreenlayout.ui.question

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.mainscreenlayout.domain.Answer
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.model.FirestoreRepository
import com.example.mainscreenlayout.domain.Question
import com.example.mainscreenlayout.model.PersonalDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class QuestionViewModel : ViewModel() {

    private val repository = FirestoreRepository()
    private val questions = MediatorLiveData<List<Question>>()
    private val answers = ArrayList<String>()

    private val index = MutableLiveData<Int>()
    private val selected = index.map {
        questions.value?.get(it)
    }

    init {
        answers.addAll(listOf("", "", ""))
        viewModelScope.launch {
            repository.getQuestions().asFlow()
                .collect {
                    questions.value = it
                    index.postValue(0)
                }
        }
    }

    fun observeSelected(owner : LifecycleOwner, observer : Observer<Question?>) {
        selected.observe(owner, observer)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun hasAnswersToday(activity: Activity) : Boolean {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return false
        val last = sharedPref.getLong("last", -1)
        val start = LocalDate.now().atTime(0, 0, 0).toEpochSecond(ZoneOffset.UTC)
        val end = LocalDate.now().atTime(23, 59, 59).toEpochSecond(ZoneOffset.UTC)
        if (last in start..end) {
            return true
        }
        return false
    }

    fun nextQuestion() : Boolean {
        val current = index.value
        if (current == 2 || current == null) {
            return false
        }
        index.value = current + 1
        return true
    }

    fun addAnswer(content : String) {
        answers.add(content)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveAnswers(activity: Activity) {
        val answer = Answer(0, answers)
        val id = PersonalDatabase.getInstance(activity).dao().addAnswer(answer)
        val historyItem = HistoryItem(0, -1, id, "Вы прошли опросник эмоционального состояния", LocalDateTime.now().format(
            DateTimeFormatter.BASIC_ISO_DATE))
        PersonalDatabase.getInstance(activity).dao().addHistoryItem(historyItem)
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        with (sharedPref.edit()) {
            putLong("last", now)
            apply()
        }
    }
}