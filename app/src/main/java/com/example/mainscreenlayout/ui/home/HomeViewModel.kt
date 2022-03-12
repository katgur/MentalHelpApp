package com.example.mainscreenlayout.ui.home

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.example.mainscreenlayout.model.FirestoreRepository
import com.example.mainscreenlayout.model.RoomRepository

class HomeViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val roomRepository = RoomRepository()

    private val exercises = MediatorLiveData<List<String>>()
    private val packs = MediatorLiveData<List<String>>()
    private val recommended = MediatorLiveData<List<String>>()

    fun observeExercises(owner: LifecycleOwner, observer: Observer<List<String>>) {
        exercises.observe(owner, observer)
        exercises.addSource(firestoreRepository.getExercises()) {
            exercises.setValue(it)
        }
    }

    fun observePacks(owner: LifecycleOwner, observer: Observer<List<String>>) {
        packs.observe(owner, observer)
        packs.addSource(firestoreRepository.getPacks()) {
            packs.value = it
        }
    }

    fun observeRecommended(owner: LifecycleOwner, observer: Observer<List<String>>) {
        recommended.observe(owner, observer)

        recommended.addSource(roomRepository.getRecommended()) {
            recommended.setValue(it)
        }
    }

    fun onRecommendedClick(content: String) {
        //todo
    }

    fun onExerciseClick(content: String) {
        //todo
    }

    fun onPackClick(content: String) {
        //todo
    }
}