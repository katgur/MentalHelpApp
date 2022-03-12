package com.example.mainscreenlayout.ui.question

import androidx.lifecycle.*
import com.example.mainscreenlayout.model.FirestoreRepository
import com.example.mainscreenlayout.model.Question
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val repository = FirestoreRepository()
    private val questions = MediatorLiveData<List<Question>>()

    private val index = MutableLiveData<Int>()
    private val selected = index.map {
        questions.value?.get(it)
    }

    init {
        viewModelScope.launch {
            repository.getQuestions().asFlow()
                .collect {
                    questions.value = it
                    index.postValue(0)
                }
        }
    }

    fun observeSelected(owner : LifecycleOwner, observer : Observer<Question?>) {
        selected.observe(owner, observer)
    }

    fun nextQuestion() : Boolean {
        val current = index.value
        if (current == 2 || current == null) {
            return false
        }
        index.value = current + 1
        return true
    }
}