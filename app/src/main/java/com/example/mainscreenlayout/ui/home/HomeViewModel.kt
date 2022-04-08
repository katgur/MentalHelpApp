package com.example.mainscreenlayout.ui.home

import android.content.Context
import androidx.lifecycle.*
import com.example.mainscreenlayout.domain.GamificationSystem
import com.example.mainscreenlayout.model.MarkableItem
import com.example.mainscreenlayout.domain.Recommendation
import com.example.mainscreenlayout.data.FirestoreRepository
import com.example.mainscreenlayout.data.PersonalDatabase
import com.example.mainscreenlayout.utils.QueryUtils

class HomeViewModel(context : Context) : ViewModel() {

    private val firestoreRepository = FirestoreRepository()
    private val recommendationSystem = Recommendation(context)

    private val exercises = MediatorLiveData<List<MarkableItem>>()
    private val packs = MediatorLiveData<List<MarkableItem>>()

    fun getExercises(context: Context, packId: String? = null) : ArrayList<MarkableItem> {
        if (exercises.value == null) {
            return ArrayList()
        }
        val result = ArrayList<MarkableItem>()
        if (packId == null) {
            for (exercise in exercises.value!!) {
                result.add(exercise)
            }
        } else {
            for (exercise in exercises.value!!) {
                if (QueryUtils.packToExercise[packId]?.contains(exercise.id) == true) {
                    result.add(exercise)
                }
            }
        }
        val favourites = PersonalDatabase.getInstance(context).dao().getAllFavourites()
        for (markedItem in favourites) {
            for (markableItem in result) {
                if (markableItem.id == markedItem.exercise_id) {
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

    fun observeRecommended(owner: LifecycleOwner, observer: Observer<List<MarkableItem>?>) {
        recommendationSystem.observeRecommended(owner, observer)
    }

    fun observePoints(owner : LifecycleOwner, observer : Observer<Pair<Int, Int>>, context : Context) {
        GamificationSystem.getInstance(context).points.observe(owner, observer)
    }
}