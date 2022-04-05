package com.example.mainscreenlayout.ui.chat

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mainscreenlayout.domain.ExerciseRepository

class ChatViewModelFactory(val id : String,
                           private val application : Application,
                           val owner: LifecycleOwner): ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            ChatViewModel(ExerciseRepository(id), application, owner) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}