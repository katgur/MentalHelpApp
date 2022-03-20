package com.example.mainscreenlayout.ui.chat

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mainscreenlayout.model.ExerciseRepository

class ChatViewModelFactory(val id : String, val application : Application): ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            ChatViewModel(ExerciseRepository(id), application) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}