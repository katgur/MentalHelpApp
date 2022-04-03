package com.example.mainscreenlayout.ui.exercise

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.domain.MarkedItem
import com.example.mainscreenlayout.model.PersonalDatabase
import com.example.mainscreenlayout.utils.QueryUtils

class ExerciseListViewModel : ViewModel() {

    fun markExercise(context: Context, id: String) {
        //todo switch favourite exercise: twice click is mischoosing
        val markedItem = MarkedItem(null, id,"Вам понравилось упражнение " + QueryUtils.idToName[id])
        PersonalDatabase.getInstance(context).dao().addFavourite(markedItem)
    }
}