package com.example.mainscreenlayout.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mainscreenlayout.domain.Answer
import java.time.LocalDateTime
import java.time.ZoneOffset

class RoomRepository {

    fun getLastAnswer(context : Context) : Pair<LocalDateTime, Answer> {
        val lastHistoryAnswer = PersonalDatabase.getInstance(context).dao().getLastAnswer()
        val lastDateTime = LocalDateTime.ofEpochSecond(lastHistoryAnswer.keys.toList()[0].date,0, ZoneOffset.UTC)
        val lastAnswer = lastHistoryAnswer.values.toList()[0][0]
        return Pair(lastDateTime, lastAnswer)
    }
}