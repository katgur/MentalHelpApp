package com.example.mainscreenlayout.model

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData

class FirestoreRepository {

    val questions = ArrayList<Question>()

    fun getExercises(): LiveData<List<String>> {
        return FirestoreDatabase.get("exercises/name")
    }

    fun getPacks(): LiveData<List<String>> {
        return FirestoreDatabase.get("packs/name")
    }

    fun getQuestions() : LiveData<List<Question>> {
        return FirestoreDatabase.get1("questions")
    }
}