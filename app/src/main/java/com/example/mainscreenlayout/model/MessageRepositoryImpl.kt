package com.example.mainscreenlayout.model

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MessageRepositoryImpl(private val messages: MediatorLiveData<ArrayList<Message>> = MediatorLiveData()) {

    init {
        messages.value = ArrayList()
    }

    fun addMessage(message: Message) {
        messages.value?.add(message)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<ArrayList<Message>>) {
        messages.observe(owner, observer)
    }

    fun addSource(liveData: LiveData<Message>, observer: Observer<ArrayList<Message>>) {
        messages.addSource(liveData, observer)
    }

    fun setValue(value: Message) {
        messages.value = value
    }
}