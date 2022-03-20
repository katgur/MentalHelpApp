package com.example.mainscreenlayout.ui.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecordViewModelFactory(private val id : Long) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecordViewModel(id) as T
    }
}