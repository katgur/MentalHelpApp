package com.example.mainscreenlayout.ui.chat

import androidx.lifecycle.*
import com.example.mainscreenlayout.model.*
import kotlinx.coroutines.launch

class ChatViewModel(private val exercise: ExerciseRepository) : ViewModel() {

    var isBot: Boolean = true

    private val messageRepository: IMessageRepository = MessageRepositoryImpl()
    val commandRepository: CommandRepository = CommandRepository()

    fun observeMessages(owner: LifecycleOwner, observer: Observer<Message>) {
        messageRepository.observe(owner, observer)
        messageRepository.addSource(exercise.steps) {
            messageRepository.setValue(it)
        }
    }

    fun observeCommands(owner: LifecycleOwner, observer: Observer<String>) {
        commandRepository.observe(owner, observer)
    }

    fun processCommand(command: String) {
        messageRepository.addMessage(Message(command, "me", 0))
        if (command == "Эффективность") {
            viewModelScope.launch {
                exercise.addEfficiencyStep()
            }
        }
        else if (command == "Время освоения") {
            viewModelScope.launch {
                exercise.addDurationStep()
            }
        }
    }

    fun processMessage(message: Message) {
        messageRepository.addMessage(message)
        // todo save into table
    }
}