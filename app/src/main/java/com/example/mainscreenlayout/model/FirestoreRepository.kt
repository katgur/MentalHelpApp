package com.example.mainscreenlayout.data

import androidx.lifecycle.LiveData
import com.example.mainscreenlayout.model.Question

class FirestoreRepository {

    val questions = ArrayList<Question>()

    fun getExercises(): LiveData<Any> {
        return FirestoreDatabase.get("exercises/name")
    }

    fun getPacks(): LiveData<Any> {
        return FirestoreDatabase.get("packs/name")
    }

    fun getQuestions() : LiveData<List<Question>> {
        return FirestoreDatabase.get1("questions")
    }
}