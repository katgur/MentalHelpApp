package com.example.mainscreenlayout.ui.dashboard

import android.content.Context
import androidx.lifecycle.*
import com.example.mainscreenlayout.model.Answer
import com.example.mainscreenlayout.data.PersonalDatabase
import com.example.mainscreenlayout.model.RoomRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class DashboardViewModel : ViewModel() {

    private val roomRepository = RoomRepository()
    private val date = MutableLiveData<LocalDate>()

    init {
        date.value = LocalDate.now()
    }

    fun observeDate(owner : LifecycleOwner, observer : Observer<LocalDate>) {
        date.observe(owner, observer)
    }

    fun setNextMonth() {
        date.postValue(date.value?.plusMonths(1))
    }

    fun setPreviousMonth() {
        date.postValue(date.value?.minusMonths(1))
    }

    fun getData(context : Context, date : LocalDate) : Map<LocalDate, Answer> {
        val start = LocalDateTime.of(date.year, date.month, 1, 0, 0, 0)
            .toEpochSecond(ZoneOffset.ofHours(3))
        val end = LocalDateTime.of(date.year, date.month, date.month.length(date.isLeapYear), 23, 59, 59)
            .toEpochSecond(ZoneOffset.ofHours(3))
        val answers = roomRepository.getAnswersIn(context, start, end)
        val data = answers.entries.associate { LocalDateTime.ofEpochSecond(it.key.date, 0, ZoneOffset.UTC).toLocalDate() to it.value[0] }
        return data
    }
}