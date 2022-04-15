package com.example.mainscreenlayout.model

import androidx.lifecycle.LiveData
import com.example.mainscreenlayout.model.FirestoreDatabase
import com.example.mainscreenlayout.model.Question
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import org.w3c.dom.Document

class FirestoreRepository {

    fun getExercises(): LiveData<Any> {
        return FirestoreDatabase.getAny("exercises/name")
    }

    fun getPacks(): LiveData<Any> {
        return FirestoreDatabase.getAny("packs/name")
    }

    fun getQuestions() : LiveData<List<Question>> {
        return FirestoreDatabase.getQuestions("questions")
    }

    fun getExercise(id: String): Task<DocumentSnapshot> {
        return FirestoreDatabase.getTask("exercises/$id")
    }

    fun getPack(id: String): Task<DocumentSnapshot> {
        return FirestoreDatabase.getTask("packs/$id")
    }
}