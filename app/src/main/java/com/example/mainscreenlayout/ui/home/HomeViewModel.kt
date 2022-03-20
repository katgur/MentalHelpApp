package com.example.mainscreenlayout.ui.home

import android.content.Context
import androidx.lifecycle.*
import com.example.mainscreenlayout.domain.MarkableItem
import com.example.mainscreenlayout.model.FirestoreRepository
import com.example.mainscreenlayout.model.PersonalDatabase
import com.example.mainscreenlayout.model.RoomRepository

class HomeViewModel : ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val roomRepository = RoomRepository()

    private val exercises = MediatorLiveData<List<MarkableItem>>()
    private val packs = MediatorLiveData<List<MarkableItem>>()
    private val recommended = MediatorLiveData<List<MarkableItem>>()

    fun getExercises(context : Context) : ArrayList<MarkableItem> {
        if (exercises.value == null) {
            return ArrayList()
        }
        val result = ArrayList<MarkableItem>()
        for (exercise in exercises.value!!) {
            result.add(exercise)
        }
        val favourites = PersonalDatabase.getInstance(context).dao().getFavourites()
        //todo optimize
        for (markedItem in favourites) {
            for (markableItem in result) {
                if (markableItem.id == markedItem.item_id) {
                    markableItem.isMarked = true
                }
            }
        }
        return result
    }

    fun observeExercises(owner: LifecycleOwner, observer: Observer<List<MarkableItem>>) {
        exercises.observe(owner, observer)
        exercises.addSource(firestoreRepository.getExercises()) {
            val response = it as List<MarkableItem>
            exercises.setValue(response)
        }
    }

    fun observePacks(owner: LifecycleOwner, observer: Observer<List<MarkableItem>>) {
        packs.observe(owner, observer)
        packs.addSource(firestoreRepository.getPacks()) {
            val response = it as List<MarkableItem>
            packs.setValue(response)
        }
    }

    fun observeRecommended(owner: LifecycleOwner, observer: Observer<List<MarkableItem>>) {
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