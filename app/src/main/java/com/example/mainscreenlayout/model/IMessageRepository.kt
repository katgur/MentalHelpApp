package com.example.mainscreenlayout.model

import androidx.lifecycle.MutableLiveData

interface IMessageRepository {

    var messages: MutableLiveData<Message>

    fun addMessage(message: Message)
}