package com.example.mainscreenlayout.ui.history

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.model.HistoryItem
import com.example.mainscreenlayout.data.PersonalDatabase

class HistoryViewModel : ViewModel() {

    private val history = MutableLiveData<ArrayList<HistoryItem>>()

    fun load(context: Context) {
        val history = PersonalDatabase.getInstance(context).dao().getAllHistory()
        this.history.postValue(ArrayList(history))
    }

    fun observeHistory(owner: LifecycleOwner, observer: Observer<ArrayList<HistoryItem>>) {
        this.history.observe(owner, observer)
    }
}