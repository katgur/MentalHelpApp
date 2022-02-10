package com.example.mainscreenlayout.model

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreDatabase(private val context: FragmentActivity?) {

    private val TAG: String = this.javaClass.simpleName

    init {
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
    }

    fun get(query: String) : LiveData<List<String>> {

        val data = MediatorLiveData<List<String>>()
        val db = Firebase.firestore

        val parts = query.split("/")

        db.collection(parts[0])
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "Got access to " + parts[0] + " collection")

                val response = ArrayList<String>()
                response.add(result.documents[0].id)
                data.postValue(response)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents.", exception)

                data.postValue(listOf("kek", "lol", "cheburek"))
            }

        return data
    }
}