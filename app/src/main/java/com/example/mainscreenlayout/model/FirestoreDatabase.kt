package com.example.mainscreenlayout.model

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

object FirestoreDatabase {

    private val TAG: String = this.javaClass.simpleName
    private var authorized = false

    fun getInstance(context: FragmentActivity?) : FirestoreDatabase {
        if (!authorized) {
            auth(context)
        }
        return this
    }

    fun get(query: String) : LiveData<List<String>> {
        val data = MutableLiveData<List<String>>()
        val db = Firebase.firestore
        val parts = query.split("/")

        db.collection(parts[0])
            .get()
            .addOnSuccessListener { result ->
                val response = ArrayList<String>()
                if (parts.size == 2) {
                    for (document in result) {
                        val response1 = document.get(parts[1]).toString()
                        response.add(response1)
                    }
                } else if (parts.size == 3) {
                    for (document in result) {
                        if (document.get("id") == parts[1]) {
                            val response1 = document.get(parts[2]).toString()
                            response.add(response1)
                        }
                    }
                }
                data.postValue(response)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents.", exception)
            }

        return data
    }

    private fun auth(context: FragmentActivity?) {
        val auth = Firebase.auth
        if (context == null) {
            Log.d(TAG, "init:contextIsNull")
        } else {
            auth.signInAnonymously()
                .addOnCompleteListener(context) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInAnonymously:success")
                    } else {
                        Log.d(TAG, "signInAnonymously:failure", task.exception)
                    }
                }
        }
        authorized = true
    }
}