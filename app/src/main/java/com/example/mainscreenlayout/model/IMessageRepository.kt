package com.example.mainscreenlayout.model

import androidx.lifecycle.*

interface IMessageRepository {

    var messages: MediatorLiveData<Message>

    fun addMessage(message: Message)

    fun observe(owner: LifecycleOwner, observer: Observer<Message>)

    fun addSource(liveData: LiveData<Message>, observer: Observer<Message>)

    fun setValue(value: Message)
}