package com.example.mainscreenlayout.ui.chat

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.mainscreenlayout.data.MessageRepositoryImpl
import com.example.mainscreenlayout.domain.ExerciseRepository
import com.example.mainscreenlayout.model.Message

@RequiresApi(Build.VERSION_CODES.O)
class ChatViewModel(private val exerciseRepository: ExerciseRepository, application: Application, owner: LifecycleOwner) : AndroidViewModel(application) {

    private val messageRepository = MessageRepositoryImpl()

    init {
        exerciseRepository.load(application.applicationContext, owner)
    }

    fun observeMessages(owner: LifecycleOwner, observer: Observer<Message>) {
        messageRepository.observe(owner, observer)
        messageRepository.addSource(exerciseRepository.steps) {
            messageRepository.addMessage(it)
        }
    }

    fun observeCommands(owner: LifecycleOwner, observer: Observer<List<String>>) {
        exerciseRepository.observeCommands(owner, observer)
    }

    fun observeEnterMode(owner: LifecycleOwner, observer: Observer<Boolean>) {
        exerciseRepository.enterMode.observe(owner, observer)
    }

    fun processCommand(command: String) {
        messageRepository.addMessage(Message(command, "me", 0))
        when (command) {
            "Эффективность" -> exerciseRepository.addEfficiencyStep()
            "Время" -> exerciseRepository.addDurationStep()
            "Далее" -> exerciseRepository.addNextStep()
            "Рекомендации" -> exerciseRepository.addRecommendation()
            "Скажи что-нибудь" -> exerciseRepository.addQuote()
            "Помощь" -> exerciseRepository.addHelp()
            "Расскажи о панике" -> exerciseRepository.addHelp()
        }
    }

    fun processMessage(message: Message) {
        messageRepository.addMessage(message)
        exerciseRepository.addToRecord(message.content)
        exerciseRepository.refreshCommands()
    }
}