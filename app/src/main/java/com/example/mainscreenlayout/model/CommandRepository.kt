package com.example.mainscreenlayout.model

import androidx.lifecycle.*

class CommandRepository {

    private val commands = MediatorLiveData<List<String>>()

    fun setCommands(commands : List<String>) {
        this.commands.postValue(commands)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<String>>) {
        commands.observe(owner, observer)
    }
}