package com.example.mainscreenlayout.model

import androidx.lifecycle.*

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