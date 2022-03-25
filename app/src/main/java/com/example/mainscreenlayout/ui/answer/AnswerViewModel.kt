package com.example.mainscreenlayout.ui.answer

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.model.PersonalDatabase

class AnswerViewModel : ViewModel() {

    fun getContent(context : Context, id : String) : String {
        val answer = PersonalDatabase.getInstance(context).dao().getAnswer(id)
        return answer.answers.joinToString(separator = "\n")
    }

    fun deleteAnswer(context : Context, historyItem: HistoryItem) {
        val answer = historyItem.answer_id?.let {
            PersonalDatabase.getInstance(context).dao().getAnswer(
                it
            )
        }
        if (answer != null) {
            PersonalDatabase.getInstance(context).dao().deleteAnswer(answer)
        }
    }
}