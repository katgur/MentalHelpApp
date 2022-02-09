package com.example.mainscreenlayout.model

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mainscreenlayout.ui.chat.ChatFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.ExecutorService

class FirestoreDatabase(private val context: FragmentActivity?) {

    private val TAG: String = this.javaClass.simpleName

    private var observers: ArrayList<IObserver> = ArrayList()

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

    fun subscribe(observer: IObserver) {
        observers.add(observer)
    }

    fun get(query: String) {
        val db = Firebase.firestore

        val parts = query.split("/")

        db.collection(parts[0])
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "Got access to " + parts[0] + " collection")

                val response = ArrayList<String>()
                response.add(result.documents[0].get(parts[1]).toString())
                for (observer in observers) {
                    observer.onResponse(response)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents.", exception)
            }
    }
}