package com.example.mainscreenlayout.ui.chat

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.model.*

class ChatViewModel(context: FragmentActivity?) : ViewModel() {
    
    val chat: Chat = Chat()

    fun sendMessage(query: String) {
        messageRepository.addMessage(query)
    }

    fun addCommands(query: String) {
        commandRepository.addDefaultCommands()
    }
}