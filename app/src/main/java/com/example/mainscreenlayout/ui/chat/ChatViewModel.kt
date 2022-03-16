package com.example.mainscreenlayout.ui.chat

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.mainscreenlayout.model.*

class ChatViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {

    private val messageRepository = MessageRepositoryImpl()

    init {
        exerciseRepository.load()
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

    fun processCommand(command: String) {
        messageRepository.addMessage(Message(command, "me", 0))
        when (command) {
            "Эффективность" -> exerciseRepository.addEfficiencyStep()
            "Время" -> exerciseRepository.addDurationStep()
            "Далее" -> exerciseRepository.addNextStep()
        }
    }

    fun processMessage(message: Message) {
        messageRepository.addMessage(message)
        exerciseRepository.addToRecord(message.content)
        exerciseRepository.refreshCommands()
    }
}