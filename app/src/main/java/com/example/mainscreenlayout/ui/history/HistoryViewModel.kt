package com.example.mainscreenlayout.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.domain.HistoryItem
import com.example.mainscreenlayout.model.PersonalDatabase

class HistoryViewModel : ViewModel() {

    fun getHistory(context : Context) : List<HistoryItem> {
        // todo create repository
        return PersonalDatabase.getInstance(context).dao().getAllHistory()
    }
}