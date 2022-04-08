package com.example.mainscreenlayout.ui.exercise

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.model.MarkedItem
import com.example.mainscreenlayout.data.PersonalDatabase
import com.example.mainscreenlayout.utils.QueryUtils

class ExerciseListViewModel : ViewModel() {

    fun markExercise(context: Context, id: String) {
        val markedItem = MarkedItem(null, id,"Вам понравилось упражнение " + QueryUtils.idToName[id])
        PersonalDatabase.getInstance(context).dao().addFavourite(markedItem)
    }
}