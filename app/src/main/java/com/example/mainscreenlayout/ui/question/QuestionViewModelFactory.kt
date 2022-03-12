package com.example.mainscreenlayout.ui.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mainscreenlayout.ui.home.HomeViewModel

class QuestionViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            QuestionViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}