package com.example.mainscreenlayout.ui.exercise

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mainscreenlayout.domain.MarkedItem
import com.example.mainscreenlayout.model.PersonalDatabase

class ExerciseListViewModel : ViewModel() {

    fun markExercise(context : Context, id : String) {
        //todo map id to name
        val markedItem = MarkedItem(0, id, "Вам понравилось упражнение $id")
        PersonalDatabase.getInstance(context).dao().addFavourite(markedItem)
    }
}