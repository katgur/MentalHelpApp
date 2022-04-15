package com.example.mainscreenlayout.ui.chat

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.mainscreenlayout.data.MessageRepositoryImpl
import com.example.mainscreenlayout.domain.Exercise
import com.example.mainscreenlayout.model.Message

@RequiresApi(Build.VERSION_CODES.O)
class ChatViewModel(private val exercise: Exercise, application: Application, owner: LifecycleOwner) : AndroidViewModel(application) {

    private val messageRepository = MessageRepositoryImpl()

    init {
        exercise.load(application.applicationContext, owner)
    }

    fun observeMessages(owner: LifecycleOwner, observer: Observer<Message>) {
        messageRepository.observe(owner, observer)
        messageRepository.addSource(exercise.steps) {
            messageRepository.addMessage(it)
        }
    }

    fun observeCommands(owner: LifecycleOwner, observer: Observer<List<String>>) {
        exercise.observeCommands(owner, observer)
    }

    fun observeEnterMode(owner: LifecycleOwner, observer: Observer<Boolean>) {
        exercise.enterMode.observe(owner, observer)
    }

    fun processCommand(command: String) {
        messageRepository.addMessage(Message(command, "me", 0))
        when (command) {
            "Эффективность" -> exercise.addEfficiencyStep()
            "Время" -> exercise.addDurationStep()
            "Далее" -> exercise.addNextStep()
            "Рекомендации" -> exercise.addRecommendation()
            "Скажи что-нибудь" -> exercise.addQuote()
            "Помощь" -> exercise.addHelp()
            "Расскажи о панике" -> exercise.addHelp()
        }
    }

    fun processMessage(message: Message) {
        messageRepository.addMessage(message)
        exercise.addToRecord(message.content)
        exercise.refreshCommands()
    }
}