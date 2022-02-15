package com.example.mainscreenlayout.ui.chat

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mainscreenlayout.model.ExerciseRepository

class ChatViewModelFactory(private val context: FragmentActivity?): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            ChatViewModel(ExerciseRepository("thought_journal")) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}