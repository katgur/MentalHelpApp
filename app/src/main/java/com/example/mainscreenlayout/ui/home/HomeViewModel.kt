package com.example.mainscreenlayout.ui.home

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.example.mainscreenlayout.model.FirestoreRepository

class HomeViewModel(context: FragmentActivity?) : ViewModel() {

    private val firestoreRepository = FirestoreRepository(context)

    var exercises = MediatorLiveData<List<String>>()

    fun observe(owner: LifecycleOwner, observer: Observer<List<String>>) {
        exercises.observe(owner, observer)

        exercises.addSource(firestoreRepository.getExercises()) {
            exercises.setValue(it)
        }
    }
}