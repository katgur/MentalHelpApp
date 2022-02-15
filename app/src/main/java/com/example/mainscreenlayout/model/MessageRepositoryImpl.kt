package com.example.mainscreenlayout.model

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MessageRepositoryImpl(override var messages: MediatorLiveData<Message> = MediatorLiveData()) : IMessageRepository {

    override fun addMessage(message: Message) {
        messages.postValue(message)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<Message>) {
        messages.observe(owner, observer)
    }

    override fun addSource(liveData: LiveData<Message>, observer: Observer<Message>) {
        messages.addSource(liveData, observer)
    }

    override fun setValue(value: Message) {
        messages.value = value
    }
}