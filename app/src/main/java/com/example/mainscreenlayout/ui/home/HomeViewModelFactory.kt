package com.example.mainscreenlayout.ui.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mainscreenlayout.model.Exercise
import com.example.mainscreenlayout.ui.chat.ChatViewModel

class HomeViewModelFactory(private val context: FragmentActivity?) : ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(context) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}