package com.example.mainscreenlayout.model

import androidx.lifecycle.MutableLiveData

class CommandRepository {

    var commands: MutableLiveData<String> = MutableLiveData<String>("Начать чат")

    fun addDefaultCommands() {
        commands.postValue("Выйти из чата")
    }
}