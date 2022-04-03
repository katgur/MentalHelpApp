package com.example.mainscreenlayout.domain

import android.content.Context
import androidx.lifecycle.*
import com.example.mainscreenlayout.model.FirestoreDatabase
import com.example.mainscreenlayout.model.RoomRepository
import com.google.firebase.firestore.DocumentReference

class Recommendation {

    private val roomRepository = RoomRepository()
    private val recommended = MediatorLiveData<List<MarkableItem>>()
    private val exercisesPaths = MutableLiveData<List<String>>()

    init {
        recommended.postValue(listOf())
        exercisesPaths.postValue(listOf())
    }

    fun getRecommended(): List<MarkableItem> {
        return recommended.value!!
    }

    fun observeRecommended(owner: LifecycleOwner, observer: Observer<List<MarkableItem>>, context : Context) {
        recommended.observe(owner, observer)
        recommended.addSource(getRecommended(context)) {
            val response = it as List<MarkableItem>
            recommended.setValue(response)
        }
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
        val pack = FirestoreDatabase.alternativeGet("packs/$packName")
        pack.addOnSuccessListener { doc ->
            val exercises = (doc["content"] as List<DocumentReference>).map { reference ->
                reference.get().addOnSuccessListener {
                    val item = MarkableItem(it.id, it["name"] as String, false)
                    value.add(item)
                    res.postValue(value)
                }
            }
        }

        return res
    }
}