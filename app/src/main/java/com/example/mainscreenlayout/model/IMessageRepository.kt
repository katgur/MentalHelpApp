package com.example.mainscreenlayout.model

import androidx.lifecycle.*
import com.example.mainscreenlayout.domain.Message

interface IMessageRepository {

    var messages: MediatorLiveData<ArrayList<Message>>

    fun addMessage(message: Message)

    fun observe(owner: LifecycleOwner, observer: Observer<Message>)

    fun addSource(liveData: LiveData<Message>, observer: Observer<Message>)

    fun setValue(value: Message)
}