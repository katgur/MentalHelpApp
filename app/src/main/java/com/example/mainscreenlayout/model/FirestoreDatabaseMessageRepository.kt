package com.example.mainscreenlayout.model

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.example.mainscreenlayout.utils.QueryUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreDatabaseMessageRepository(context: FragmentActivity?,
                                         override var messages: MutableLiveData<Message>) : IMessageRepository {

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

    override fun addMessage(path: String) {
        val db = Firebase.firestore
        val parts = path.split(QueryUtils.delimeter)

        db.collection(parts[0])
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "Got access to " + parts[0] + " collection")
                val content = result.documents[0].get(parts[1]).toString()
                val message = Message(content, User("bot"), 0)
                messages.postValue(message)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents.", exception)
            }
    }
}