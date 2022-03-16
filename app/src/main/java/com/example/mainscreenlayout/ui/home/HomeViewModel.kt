package com.example.mainscreenlayout.ui.home

import androidx.lifecycle.*
import com.example.mainscreenlayout.R
import com.example.mainscreenlayout.model.FirestoreRepository
import com.example.mainscreenlayout.model.RoomRepository
import com.example.mainscreenlayout.ui.nick.NicknameFragment
import com.example.mainscreenlayout.utils.QueryUtils

class HomeViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val roomRepository = RoomRepository()

    private val exercises = MediatorLiveData<List<String>>()
    private val packs = MediatorLiveData<List<String>>()
    private val recommended = MediatorLiveData<List<String>>()

    fun observeExercises(owner: LifecycleOwner, observer: Observer<List<String>>) {
        exercises.observe(owner, observer)
        exercises.addSource(firestoreRepository.getExercises()) {
            val response = it as List<String>
            exercises.setValue(response)
        }
    }

    fun observePacks(owner: LifecycleOwner, observer: Observer<List<String>>) {
        packs.observe(owner, observer)
        packs.addSource(firestoreRepository.getPacks()) {
            val response = it as List<String>
            packs.setValue(response)
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

//    fun onExerciseClick(content: String) {
//        //todo
//        val id = QueryUtils.nameToId[content]
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container_view, NicknameFragment.newInstance())
//            .disallowAddToBackStack()
//            .commit()
//    }

    fun onPackClick(content: String) {
        //todo
    }
}