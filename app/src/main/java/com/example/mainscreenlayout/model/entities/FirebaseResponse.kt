package com.example.mainscreenlayout.model.entities

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

class FirebaseResponse(private val response: Task<DocumentSnapshot>) {

    fun get() {

    }
}