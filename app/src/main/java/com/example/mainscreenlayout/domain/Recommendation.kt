package com.example.mainscreenlayout.domain

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.mainscreenlayout.model.FirestoreDatabase
import com.example.mainscreenlayout.model.FirestoreRepository
import com.example.mainscreenlayout.model.RoomRepository
import com.example.mainscreenlayout.model.MarkableItem
import com.google.firebase.firestore.DocumentReference

class Recommendation(context: Context) {

    private val firestoreRepository = FirestoreRepository()
    private val roomRepository = RoomRepository()
    private val recommended = MediatorLiveData<List<MarkableItem>?>()
    private val exercisesPaths = MutableLiveData<List<String>>()

    init {
        recommended.postValue(null)
        exercisesPaths.postValue(listOf())
        recommended.addSource(getRecommended(context)) {
            val response = it as List<MarkableItem>
            Log.d("Recommendation.load", "got response " + response.size)
            recommended.postValue(response)
        }
    }

    fun observeRecommended(owner: LifecycleOwner, observer: Observer<List<MarkableItem>?>) {
        recommended.observe(owner, observer)
    }

    private fun getRecommended(context: Context): LiveData<Any> {
        val res = MutableLiveData<Any>()
        val value = arrayListOf<MarkableItem>()

        val lastAnswer = roomRepository.getLastAnswer(context) ?: return res
        val packName = when (lastAnswer.second.problem) {
            "Прокрастинация" -> "procrastination"
            "Тревога" -> "anxiety"
            "Депрессия" -> "depression"
            else -> "low_self_esteem"
        }
        val pack = firestoreRepository.getPack(packName)
        pack.addOnSuccessListener { doc ->
            val exercises = (doc["content"] as List<DocumentReference>).map { reference ->
                reference.get().addOnSuccessListener {
                    val item = MarkableItem(it.id, it["name"] as String, false)
                    Log.d("Recommendation.getRecommended", "got response " + item.name)
                    value.add(item)
                    res.postValue(value)
                }
            }
        }

        return res
    }
}