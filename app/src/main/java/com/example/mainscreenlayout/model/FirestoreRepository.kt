package com.example.mainscreenlayout.data

import androidx.lifecycle.LiveData
import com.example.mainscreenlayout.model.FirestoreDatabase
import com.example.mainscreenlayout.model.Question

class FirestoreRepository {

    val questions = ArrayList<Question>()

    fun getExercises(): LiveData<Any> {
        return FirestoreDatabase.getAny("exercises/name")
    }

    fun getPacks(): LiveData<Any> {
        return FirestoreDatabase.getAny("packs/name")
    }

    fun getQuestions() : LiveData<List<Question>> {
        return FirestoreDatabase.getQuestions("questions")
    }
}