package com.example.mainscreenlayout.data

import androidx.lifecycle.*
import com.example.mainscreenlayout.model.Message

class MessageRepositoryImpl(private val messages: MediatorLiveData<Message> = MediatorLiveData()) {

    fun observe(owner: LifecycleOwner, observer: Observer<Message>) {
        messages.observe(owner, observer)
    }

    fun addSource(liveData: LiveData<Message>, observer: Observer<Message>) {
        messages.addSource(liveData, observer)
    }

    fun addMessage(value: Message) {
        messages.value = value
    }
}